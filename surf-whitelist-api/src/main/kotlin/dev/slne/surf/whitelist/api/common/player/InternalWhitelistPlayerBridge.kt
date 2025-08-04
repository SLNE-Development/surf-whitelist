package dev.slne.surf.whitelist.api.common.player

import dev.slne.surf.cloud.api.common.player.OfflineCloudPlayer
import dev.slne.surf.whitelist.api.common.InternalContextHolder
import dev.slne.surf.whitelist.api.common.MutableWhitelistEntry
import dev.slne.surf.whitelist.api.common.WhitelistEntry
import dev.slne.surf.whitelist.api.common.util.InternalApi
import org.springframework.beans.factory.getBean
import java.math.BigInteger

@InternalApi
interface InternalWhitelistPlayerBridge {

    suspend fun isWhitelisted(player: OfflineCloudPlayer): WhitelistStatus
    suspend fun getWhitelist(player: OfflineCloudPlayer): WhitelistEntry?
    suspend fun editWhitelist(
        player: OfflineCloudPlayer,
        edit: MutableWhitelistEntry.() -> Unit
    ): Boolean

    suspend fun createWhitelist(
        player: OfflineCloudPlayer,
        blocked: Boolean,
        twitchId: BigInteger?,
        discordId: BigInteger?,
        addedByDiscordId: BigInteger?,
        addedByDiscordName: String?,
        addedByDiscordAvatarUrl: String?,
    ): WhitelistEntry?


    companion object {
        internal val instance get() = InternalContextHolder.instance.context.getBean<InternalWhitelistPlayerBridge>()
    }
}