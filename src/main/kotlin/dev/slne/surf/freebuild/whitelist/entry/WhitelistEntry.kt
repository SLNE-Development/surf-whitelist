package dev.slne.surf.freebuild.whitelist.entry

import java.time.OffsetDateTime
import java.util.*

data class WhitelistEntry(
    val discordUserId: Long,
    val minecraftUuid: UUID,
    val twitchId: Long?,
    val blocked: Boolean,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)