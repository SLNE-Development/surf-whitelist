@file:OptIn(InternalApi::class)

package dev.slne.surf.whitelist.api.common.player

import dev.slne.surf.cloud.api.common.player.OfflineCloudPlayer
import dev.slne.surf.whitelist.api.common.MutableWhitelistEntry
import dev.slne.surf.whitelist.api.common.WhitelistEntry
import dev.slne.surf.whitelist.api.common.util.InternalApi
import java.math.BigInteger
import java.time.ZonedDateTime

suspend fun OfflineCloudPlayer.isWhitelisted(): WhitelistStatus =
    InternalWhitelistPlayerBridge.instance.isWhitelisted(this)

suspend fun OfflineCloudPlayer.getWhitelist(): WhitelistEntry? =
    InternalWhitelistPlayerBridge.instance.getWhitelist(this)

suspend fun OfflineCloudPlayer.editWhitelist(edit: MutableWhitelistEntry.() -> Unit): Boolean =
    InternalWhitelistPlayerBridge.instance.editWhitelist(this, edit)

suspend fun OfflineCloudPlayer.createWhitelist(
    blocked: Boolean = false,
    twitchId: BigInteger?,
    discordId: BigInteger?,
    addedByDiscordId: BigInteger?,
    addedByDiscordName: String?,
    addedByDiscordAvatarUrl: String?,
): WhitelistEntry? = InternalWhitelistPlayerBridge.instance.createWhitelist(
    this,
    blocked,
    twitchId,
    discordId,
    addedByDiscordId,
    addedByDiscordName,
    addedByDiscordAvatarUrl,
)

enum class WhitelistStatus {
    NONE,
    ACTIVE,
    BLOCKED,
}