package dev.slne.surf.freebuild.whitelist.command.permission

import dev.slne.surf.api.paper.permission.PermissionRegistry

object PermissionRegistry : PermissionRegistry() {
    private const val BASE = "surf.freebuild.whitelist"
    val BYPASS = create("$BASE.bypass")

    val TOGGLE = create("$BASE.toggle")
}
