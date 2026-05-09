package dev.slne.surf.freebuild.whitelist.command.permission

import dev.slne.surf.api.paper.permission.PermissionRegistry

object PermissionRegistry : PermissionRegistry() {
    private const val BASE = "surf.freebuild.whitelist"
    private const val BASE_COMMAND = "$BASE.command"

    val COMMAND_WL_VIEW = create("$BASE_COMMAND.view")
    val BYPASS = create("$BASE.bypass")
}
