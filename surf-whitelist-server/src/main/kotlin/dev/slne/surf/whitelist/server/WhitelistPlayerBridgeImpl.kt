package dev.slne.surf.whitelist.server

import dev.slne.surf.cloud.api.common.player.OfflineCloudPlayer
import dev.slne.surf.whitelist.api.common.MutableWhitelistEntry
import dev.slne.surf.whitelist.api.common.WhitelistEntry
import dev.slne.surf.whitelist.api.common.player.InternalWhitelistPlayerBridge
import dev.slne.surf.whitelist.api.common.util.InternalApi
import dev.slne.surf.whitelist.core.WhitelistEntryImpl
import dev.slne.surf.whitelist.server.whitelist.WhitelistService
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.time.ZonedDateTime

@OptIn(InternalApi::class)
@Component
class WhitelistPlayerBridgeImpl(private val whitelistService: WhitelistService) :
    InternalWhitelistPlayerBridge {
    override suspend fun isWhitelisted(player: OfflineCloudPlayer) =
        whitelistService.isWhitelisted(player)
    override suspend fun getWhitelist(player: OfflineCloudPlayer) = whitelistService.getWhitelist(player)

    override suspend fun editWhitelist(
        player: OfflineCloudPlayer,
        edit: MutableWhitelistEntry.() -> Unit
    ) = whitelistService.editWhitelist(player, edit)

    override suspend fun createWhitelist(
        player: OfflineCloudPlayer,
        blocked: Boolean,
        twitchId: BigInteger?,
        discordId: BigInteger?,
        addedByDiscordId: BigInteger?,
        addedByDiscordName: String?,
        addedByDiscordAvatarUrl: String?,
    ) = whitelistService.createWhitelist(
        WhitelistEntryImpl(
            uuid = player.uuid,
            blocked = blocked,
            twitchId = twitchId,
            discordId = discordId,
            addedByDiscordId = addedByDiscordId,
            addedByDiscordName = addedByDiscordName,
            addedByDiscordAvatarUrl = addedByDiscordAvatarUrl,
        )
    )
}