package dev.slne.surf.freebuild.whitelist.command

import dev.jorel.commandapi.kotlindsl.booleanArgument
import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.getValue
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.freebuild.whitelist.command.permission.PermissionRegistry
import dev.slne.surf.freebuild.whitelist.config.WhitelistConfig

fun whitelistToggleCommand() = commandAPICommand("fwhitelist-toggle") {
    withPermission(PermissionRegistry.TOGGLE)

    booleanArgument("enabled")

    playerExecutor { player, arguments ->
        val enabled: Boolean by arguments

        WhitelistConfig.getConfig().whitelistEnabled = enabled
        WhitelistConfig.save()
    }
}