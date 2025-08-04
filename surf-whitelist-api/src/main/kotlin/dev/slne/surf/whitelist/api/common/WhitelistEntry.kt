package dev.slne.surf.whitelist.api.common

import java.math.BigInteger
import java.time.ZonedDateTime
import java.util.*

interface WhitelistEntry {
    val uuid: UUID
    val blocked: Boolean

    val twitchId: BigInteger?
    val discordId: BigInteger?
    val addedByDiscordId: BigInteger?
    val addedByDiscordName: String?
    val addedByDiscordAvatarUrl: String?

    val createdAt: ZonedDateTime
    val updatedAt: ZonedDateTime
}

interface MutableWhitelistEntry : WhitelistEntry {
    override var blocked: Boolean

    override var twitchId: BigInteger?
    override var discordId: BigInteger?
    override var addedByDiscordId: BigInteger?
    override var addedByDiscordName: String?
    override var addedByDiscordAvatarUrl: String?
}