package dev.slne.surf.whitelist.core

import dev.slne.surf.whitelist.api.common.MutableWhitelistEntry
import dev.slne.surf.whitelist.api.common.WhitelistEntry
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigInteger
import java.time.ZonedDateTime
import java.util.*

@Serializable
data class WhitelistEntryImpl(
    override val uuid: @Contextual UUID,
    override val blocked: Boolean,
    override val twitchId: @Contextual BigInteger?,
    override val discordId: @Contextual BigInteger?,
    override val addedByDiscordId: @Contextual BigInteger?,
    override val addedByDiscordName: String?,
    override val addedByDiscordAvatarUrl: String?,
    override val createdAt: @Contextual ZonedDateTime = ZonedDateTime.now(),
    override val updatedAt: @Contextual ZonedDateTime = ZonedDateTime.now()
) : WhitelistEntry


data class MutableWhitelistEntryImpl(
    override val uuid: UUID,
    override var blocked: Boolean,
    override var twitchId: BigInteger?,
    override var discordId: BigInteger?,
    override var addedByDiscordId: BigInteger?,
    override var addedByDiscordName: String?,
    override var addedByDiscordAvatarUrl: String?,
    override var createdAt: ZonedDateTime,
    override var updatedAt: ZonedDateTime
) : MutableWhitelistEntry
