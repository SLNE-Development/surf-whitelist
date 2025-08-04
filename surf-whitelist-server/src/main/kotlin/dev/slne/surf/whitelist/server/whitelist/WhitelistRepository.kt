package dev.slne.surf.whitelist.server.whitelist

import dev.slne.surf.cloud.api.server.plugin.CoroutineTransactional
import dev.slne.surf.whitelist.api.common.MutableWhitelistEntry
import dev.slne.surf.whitelist.core.MutableWhitelistEntryImpl
import dev.slne.surf.whitelist.core.WhitelistEntryImpl
import dev.slne.surf.whitelist.server.db.WhitelistEntity
import dev.slne.surf.whitelist.server.db.WhitelistTable
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@CoroutineTransactional
class WhitelistRepository {
    suspend fun getWhitelistStatus(uuid: UUID) = WhitelistTable.select(WhitelistTable.blocked)
        .where { WhitelistTable.uuid eq uuid }
        .singleOrNull()
        ?.let { WhitelistStatusResult(it[WhitelistTable.blocked]) }

    suspend fun getWhitelist(uuid: UUID) = WhitelistEntity
        .find { WhitelistTable.uuid eq uuid }
        .singleOrNull()
        ?.let {
            WhitelistEntryImpl(
                uuid = it.uuid,
                blocked = it.blocked,
                twitchId = it.twitchId,
                discordId = it.discordId,
                addedByDiscordId = it.addedByDiscordId,
                addedByDiscordName = it.addedByDiscordName,
                addedByDiscordAvatarUrl = it.addedByDiscordAvatarUrl,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }

    suspend fun editWhitelist(uuid: UUID, edit: MutableWhitelistEntry.() -> Unit): Boolean {
        val entity = WhitelistEntity.find { WhitelistTable.uuid eq uuid }
            .forUpdate()
            .singleOrNull() ?: return false

        val mutableEntry = MutableWhitelistEntryImpl(
            uuid = entity.uuid,
            blocked = entity.blocked,
            twitchId = entity.twitchId,
            discordId = entity.discordId,
            addedByDiscordId = entity.addedByDiscordId,
            addedByDiscordName = entity.addedByDiscordName,
            addedByDiscordAvatarUrl = entity.addedByDiscordAvatarUrl,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )

        val edited = mutableEntry.copy()
        edited.edit()

        entity.blocked = edited.blocked
        entity.twitchId = edited.twitchId
        entity.discordId = edited.discordId
        entity.addedByDiscordId = edited.addedByDiscordId
        entity.addedByDiscordName = edited.addedByDiscordName
        entity.addedByDiscordAvatarUrl = edited.addedByDiscordAvatarUrl

        return mutableEntry != edited
    }

    suspend fun createWhitelist(whitelist: WhitelistEntryImpl): WhitelistEntryImpl? {
        if (getWhitelistStatus(whitelist.uuid) != null) {
            return null
        }

        val entity = WhitelistEntity.new {
            uuid = whitelist.uuid
            blocked = whitelist.blocked
            twitchId = whitelist.twitchId
            discordId = whitelist.discordId
            addedByDiscordId = whitelist.addedByDiscordId
            addedByDiscordName = whitelist.addedByDiscordName
            addedByDiscordAvatarUrl = whitelist.addedByDiscordAvatarUrl
        }

        return WhitelistEntryImpl(
            uuid = entity.uuid,
            blocked = entity.blocked,
            twitchId = entity.twitchId,
            discordId = entity.discordId,
            addedByDiscordId = entity.addedByDiscordId,
            addedByDiscordName = entity.addedByDiscordName,
            addedByDiscordAvatarUrl = entity.addedByDiscordAvatarUrl,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }


    @JvmInline
    value class WhitelistStatusResult(val blocked: Boolean)
}