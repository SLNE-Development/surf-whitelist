package dev.slne.surf.freebuild.whitelist.listener

import dev.slne.surf.freebuild.whitelist.command.permission.PermissionRegistry
import dev.slne.surf.freebuild.whitelist.database.service.whitelistService
import dev.slne.surf.api.core.messages.adventure.appendNewline
import dev.slne.surf.api.core.messages.adventure.buildText
import kotlinx.coroutines.runBlocking
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerLoginEvent
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

object PlayerLoginListener : Listener {
    private val loginStatus = ConcurrentHashMap<UUID, LoginCheck>()
    private const val STATUS_TTL_MILLIS = 5 * 60 * 1000L

    @EventHandler
    fun onAsyncPreLogin(event: AsyncPlayerPreLoginEvent) {
        val playerUuid = event.uniqueId
        cleanupExpiredStatuses()

        runBlocking {
            val simpleWhitelist = whitelistService.findSimpleWhitelist(playerUuid)

            if (simpleWhitelist == null) {
                loginStatus[playerUuid] = LoginCheck(LoginStatus.NOT_WHITELISTED)
                return@runBlocking
            }

            loginStatus[playerUuid] = LoginCheck(if (simpleWhitelist.blocked) LoginStatus.BLOCKED else LoginStatus.ALLOWED)
        }
    }

    @EventHandler
    fun onPlayerLogin(event: PlayerLoginEvent) {
        val player = event.player
        val playerUuid = player.uniqueId

        if (player.hasPermission(PermissionRegistry.BYPASS)) {
            loginStatus.remove(playerUuid)
            return
        }

        when (loginStatus.remove(playerUuid)?.status) {
            LoginStatus.NOT_WHITELISTED -> {
                event.disallow(
                    PlayerLoginEvent.Result.KICK_WHITELIST,
                    buildKickMessage(
                        "DU BEFINDEST DICH NICHT AUF DER WHITELIST",
                        "Um auf dem Survival Server spielen zu können, musst du dich auf der Whitelist befinden. Weitere Informationen findest du im Discord."
                    )
                )
            }
            LoginStatus.BLOCKED -> {
                event.disallow(
                    PlayerLoginEvent.Result.KICK_WHITELIST,
                    buildKickMessage(
                        "DEINE WHITELIST WURDE GESPERRT",
                        "Du hast unseren Discord Server verlassen und wurdest deshalb vom Survival Server gesperrt. Wenn du weiterhin auf dem Survival Server spielen möchtest, musst du den Discord Server erneut betreten. Eine erneute Whitelist ist nicht notwendig."
                    )
                )
            }
            LoginStatus.ALLOWED -> Unit
            null -> {
                event.disallow(
                    PlayerLoginEvent.Result.KICK_WHITELIST,
                    buildKickMessage(
                        "DEINE WHITELIST KONNTE NICHT ÜBERPRÜFT WERDEN",
                        "Bitte versuche es erneut. Wenn das Problem weiterhin besteht, kontaktiere bitte den Support."
                    )
                )
            }
        }
    }

    private fun cleanupExpiredStatuses() {
        val now = System.currentTimeMillis()
        loginStatus.entries.removeIf { now - it.value.createdAtMillis > STATUS_TTL_MILLIS }
    }

    private fun buildKickMessage(header: String, reason: String) = buildText {
        appendNewline(2)
        primary("CASTCRAFTER")
        appendNewline()
        primary("COMMUNITY SERVER")
        appendNewline(2)
        error(header)
        appendNewline(3)
        spacer(reason)
        appendNewline()
        spacer("Sollte dies ein Fehler sein, kontaktiere bitte den Support.")
        appendNewline(2)
        primary("discord.gg/castcrafter")
    }

    private enum class LoginStatus {
        ALLOWED,
        NOT_WHITELISTED,
        BLOCKED
    }

    private data class LoginCheck(
        val status: LoginStatus,
        val createdAtMillis: Long = System.currentTimeMillis()
    )
}
