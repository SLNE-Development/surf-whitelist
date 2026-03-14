package dev.slne.surf.freebuild.whitelist.database.table

import dev.slne.surf.database.columns.nativeUuid
import dev.slne.surf.database.columns.time.offsetDateTime
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.dao.id.LongIdTable

object SocialsTable : LongIdTable("social_connections") {
    val discordUserId = long("discord_user_id").uniqueIndex()
    val minecraftUuid = nativeUuid("minecraft_uuid").uniqueIndex()
    val twitchId = long("twitch_id").uniqueIndex().nullable()
    val blocked = bool("blocked").default(false)
    val createdAt = offsetDateTime("created_at")
    val updatedAt = offsetDateTime("updated_at")
}