package dev.slne.surf.freebuild.whitelist.entry

import java.util.*

data class SimpleWhitelistEntry(
    val minecraftUuid: UUID,
    val blocked: Boolean
)