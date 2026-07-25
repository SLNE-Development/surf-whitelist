package dev.slne.surf.freebuild.whitelist.listener

import dev.slne.surf.api.core.luckperms.LuckPermsAccess
import dev.slne.surf.api.core.messages.adventure.buildText
import dev.slne.surf.freebuild.whitelist.command.permission.PermissionRegistry
import dev.slne.surf.freebuild.whitelist.config.WhitelistConfig
import dev.slne.surf.freebuild.whitelist.database.repository.AccountRepository
import dev.slne.surf.freebuild.whitelist.plugin
import dev.slne.surf.freebuild.whitelist.redis.RedisDiscordService
import kotlinx.coroutines.runBlocking
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import java.util.*
import java.util.logging.Level

object PlayerAsyncLoginListener : Listener {
    @EventHandler
    fun onAsyncPreLogin(event: AsyncPlayerPreLoginEvent) {
        val playerUuid = event.uniqueId

        runBlocking {
            if (hasBypassPermission(playerUuid)) {
                return@runBlocking
            }

            val discordId = AccountRepository.findDiscordAccountByMinecraftUuid(playerUuid)

            if (discordId == null) {
                event.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, buildKickMessage(
                        "DU HAST DEINEN DISCORD ACCOUNT NICHT VERKNÜPFT",
                        "Um auf dem Survival Server spielen zu können, musst du deinen Discord Account mit Minecraft verlinken. Weitere Informationen findest du unter https://auth.castcrafter.de/account?tabs=accounts"
                    )
                )
                return@runBlocking
            }

            if (!WhitelistConfig.getConfig().whitelistEnabled) {
                return@runBlocking
            }

            if (!RedisDiscordService.isDiscordMember(discordId)) {
                event.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, buildKickMessage(
                        "DU BEFINDEST DICH NICHT AUF UNSEREM DISCORD SERVER",
                        "Du befindest dich nicht auf unserem Discord Server. Um auf dem Survival Server spielen zu können, musst du unserem Discord Server beitreten. https://discord.gg/castcrafter"
                    )
                )
                return@runBlocking
            }
        }
    }

    private suspend fun hasBypassPermission(playerUuid: UUID) = try {
        val user = LuckPermsAccess.getUser(playerUuid) ?: LuckPermsAccess.loadUser(playerUuid)

        user.cachedData.getPermissionData(user.queryOptions)
            .checkPermission(PermissionRegistry.BYPASS).asBoolean()
    } catch (exception: Exception) {
        plugin.logger.log(
            Level.SEVERE,
            "Failed to resolve LuckPerms bypass permission for player $playerUuid.",
            exception
        )
        false
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
