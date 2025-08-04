package dev.slne.surf.whitelist.server.whitelist

import dev.slne.surf.cloud.api.common.player.OfflineCloudPlayer
import dev.slne.surf.whitelist.api.common.MutableWhitelistEntry
import dev.slne.surf.whitelist.api.common.player.WhitelistStatus
import dev.slne.surf.whitelist.core.WhitelistEntryImpl
import org.springframework.stereotype.Service

@Service
class WhitelistService(private val repo: WhitelistRepository) {

    suspend fun isWhitelisted(player: OfflineCloudPlayer): WhitelistStatus {
        val status = repo.getWhitelistStatus(player.uuid)
        return when {
            status == null -> WhitelistStatus.NONE
            status.blocked -> WhitelistStatus.BLOCKED
            else -> WhitelistStatus.ACTIVE
        }
    }

    suspend fun getWhitelist(player: OfflineCloudPlayer): WhitelistEntryImpl? {
        return repo.getWhitelist(player.uuid)
    }

    suspend fun editWhitelist(
        player: OfflineCloudPlayer,
        edit: MutableWhitelistEntry.() -> Unit
    ): Boolean {
        return repo.editWhitelist(player.uuid, edit)
    }

    suspend fun createWhitelist(whitelist: WhitelistEntryImpl): WhitelistEntryImpl? {
        return repo.createWhitelist(whitelist)
    }
}