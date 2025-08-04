package dev.slne.surf.whitelist.core.client

import dev.slne.surf.cloud.api.client.netty.packet.fireAndAwaitOrThrow
import dev.slne.surf.cloud.api.common.player.OfflineCloudPlayer
import dev.slne.surf.whitelist.api.common.MutableWhitelistEntry
import dev.slne.surf.whitelist.api.common.WhitelistEntry
import dev.slne.surf.whitelist.api.common.player.InternalWhitelistPlayerBridge
import dev.slne.surf.whitelist.api.common.player.WhitelistStatus
import dev.slne.surf.whitelist.api.common.util.InternalApi
import dev.slne.surf.whitelist.core.WhitelistEntryImpl
import dev.slne.surf.whitelist.core.netty.protocol.ServerboundCreateWhitelistPacket
import dev.slne.surf.whitelist.core.netty.protocol.ServerboundFetchWhitelistPacket
import dev.slne.surf.whitelist.core.netty.protocol.ServerboundIsWhitelistedPacket
import org.springframework.stereotype.Component
import java.math.BigInteger

@OptIn(InternalApi::class)
@Component
class WhitelistPlayerBridgeImpl : InternalWhitelistPlayerBridge {
    override suspend fun isWhitelisted(player: OfflineCloudPlayer): WhitelistStatus {
        return ServerboundIsWhitelistedPacket(player).fireAndAwaitOrThrow().status
    }

    override suspend fun getWhitelist(player: OfflineCloudPlayer): WhitelistEntry? {
        return ServerboundFetchWhitelistPacket(player).fireAndAwaitOrThrow().entry
    }

    override suspend fun editWhitelist(
        player: OfflineCloudPlayer,
        edit: MutableWhitelistEntry.() -> Unit
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun createWhitelist(
        player: OfflineCloudPlayer,
        blocked: Boolean,
        twitchId: BigInteger?,
        discordId: BigInteger?,
        addedByDiscordId: BigInteger?,
        addedByDiscordName: String?,
        addedByDiscordAvatarUrl: String?,
    ): WhitelistEntry? {
        val entry = WhitelistEntryImpl(
            uuid = player.uuid,
            blocked = blocked,
            twitchId = twitchId,
            discordId = discordId,
            addedByDiscordId = addedByDiscordId,
            addedByDiscordName = addedByDiscordName,
            addedByDiscordAvatarUrl = addedByDiscordAvatarUrl,
        )

        return ServerboundCreateWhitelistPacket(entry).fireAndAwaitOrThrow().entry
    }
}