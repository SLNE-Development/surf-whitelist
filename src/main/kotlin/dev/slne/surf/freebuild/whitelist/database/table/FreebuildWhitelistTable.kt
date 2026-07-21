package dev.slne.surf.freebuild.whitelist.database.table

import dev.slne.surf.database.columns.time.offsetDateTime
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import dev.slne.surf.database.table.AuditableLongIdTable

object FreebuildWhitelistTable : AuditableLongIdTable("surf-discord.freebuild_whitelists") {
    val socialConnectionId =
        ulong("social_connection_id").references(SocialConnectionsTable.id).uniqueIndex()
    val blocked = bool("blocked").default(false)
}

