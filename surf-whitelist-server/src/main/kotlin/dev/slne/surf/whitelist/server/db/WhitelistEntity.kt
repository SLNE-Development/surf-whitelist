package dev.slne.surf.whitelist.server.db

import dev.slne.surf.cloud.api.server.exposed.table.AuditableLongEntity
import dev.slne.surf.cloud.api.server.exposed.table.AuditableLongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class WhitelistEntity(id: EntityID<Long>) : AuditableLongEntity(id, WhitelistTable) {
    companion object : AuditableLongEntityClass<WhitelistEntity>(WhitelistTable)

    var uuid by WhitelistTable.uuid
    var blocked by WhitelistTable.blocked
    var twitchId by WhitelistTable.twitchId
    var discordId by WhitelistTable.discordId
    var addedByDiscordId by WhitelistTable.addedByDiscordId
    var addedByDiscordName by WhitelistTable.addedByDiscordName
    var addedByDiscordAvatarUrl by WhitelistTable.addedByDiscordAvatarUrl
}