package dev.slne.surf.freebuild.whitelist.command

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.jorel.commandapi.kotlindsl.stringArgument
import dev.slne.surf.freebuild.whitelist.command.permission.PermissionRegistry
import dev.slne.surf.freebuild.whitelist.database.service.whitelistService
import dev.slne.surf.api.paper.command.executors.anyExecutorSuspend
import dev.slne.surf.api.core.messages.adventure.clickCopiesToClipboard
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.api.core.service.PlayerLookupService

fun freebuildWhitelistCommand() = commandTree("freebuildwhitelist") {
    withPermission(PermissionRegistry.COMMAND_WL_VIEW)

    literalArgument("view") {
        stringArgument("name") {
            anyExecutorSuspend { sender, arguments ->
                val name: String by arguments
                val playerUuid = PlayerLookupService.getUuid(name)

                if (playerUuid == null) {
                    sender.sendText {
                        appendErrorPrefix()
                        error("Der Spieler wurde nicht gefunden.")
                    }
                    return@anyExecutorSuspend
                }

                val whitelist = whitelistService.findWhitelist(playerUuid)

                if (whitelist == null) {
                    sender.sendText {
                        appendErrorPrefix()
                        error("Der Spieler ist nicht auf der Whitelist.")
                    }
                    return@anyExecutorSuspend
                }

                sender.sendText {
                    appendInfoPrefix()
                    info("Der Spieler ")
                    variableValue(name)
                    spacer(" (Uuid: $playerUuid)")
                    info(" ist mit dem ")
                    append {
                        variableValue("Discord Account '${whitelist.discordUserId}'")
                        clickCopiesToClipboard(whitelist.discordUserId.toString())
                    }
                    info(" gewhitelisted.")
                    if (whitelist.blocked) {
                        appendNewErrorPrefixedLine()
                        error("Der Spieler ist aktuell blockiert.")
                    }
                }
            }
        }
    }
}