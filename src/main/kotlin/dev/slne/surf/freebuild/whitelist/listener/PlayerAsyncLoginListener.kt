package dev.slne.surf.freebuild.whitelist.listener

import dev.slne.surf.freebuild.whitelist.database.service.whitelistService
import dev.slne.surf.surfapi.core.api.messages.adventure.appendNewline
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import kotlinx.coroutines.runBlocking
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

object PlayerAsyncLoginListener : Listener {
    @EventHandler
    fun onAsyncPreLogin(event: AsyncPlayerPreLoginEvent) {
        val playerUuid = event.uniqueId

        runBlocking {
            val simpleWhitelist = whitelistService.findSimpleWhitelist(playerUuid)

            if (simpleWhitelist == null) {
                event.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                    buildKickMessage(
                        "DU BEFINDEST DICH NICHT AUF DER WHITELIST",
                        "Um auf dem Survival Server spielen zu können, musst du dich auf der Whitelist befinden. Weitere Informationen findest du im Discord."
                    )
                )
                return@runBlocking
            }

            if (simpleWhitelist.blocked) {
                event.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                    buildKickMessage(
                        "DEINE WHITELIST WURDE GESPERRT",
                        "Du hast unseren Discord Server verlassen und wurdest deshalb vom Survival Server gesperrt. Wenn du weiterhin auf dem Survival Server spielen möchtest, musst du den Discord Server erneut betreten. Eine erneute Whitelist ist nicht notwendig."
                    )
                )
            }
        }
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
}