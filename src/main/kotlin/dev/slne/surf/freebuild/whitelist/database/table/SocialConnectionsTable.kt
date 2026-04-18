package dev.slne.surf.freebuild.whitelist.database.table

import dev.slne.surf.database.columns.nativeUuid
import dev.slne.surf.database.columns.time.offsetDateTime
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import dev.slne.surf.database.table.AuditableLongIdTable

object SocialConnectionsTable : AuditableLongIdTable("social_connections_new") {
    val minecraftUuid = nativeUuid("minecraft_uuid").uniqueIndex()
    val discordUserId = long("discord_user_id").uniqueIndex().nullable()
    val twitchId = long("twitch_id").uniqueIndex().nullable()
}
