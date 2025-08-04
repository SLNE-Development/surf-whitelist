package dev.slne.surf.whitelist.server.db

import dev.slne.surf.cloud.api.server.exposed.columns.nativeUuid
import dev.slne.surf.cloud.api.server.exposed.table.AuditableLongIdTable
import java.math.BigInteger

object WhitelistTable : AuditableLongIdTable("whitelists") {
    val uuid = nativeUuid("uuid").uniqueIndex()
    val blocked = bool("blocked").default(false)
    val twitchId = varchar("twitch_id", 255)
        .transform({ BigInteger(it) }, { it.toString() })
        .nullable()

    val discordId = varchar("discord_id", 255)
        .transform({ BigInteger(it) }, { it.toString() })
        .nullable()

    val addedByDiscordId = varchar("added_by_discord_id", 255)
        .transform({ BigInteger(it) }, { it.toString() })
        .nullable()

    val addedByDiscordName = varchar("added_by_discord_name", 255).nullable()
    val addedByDiscordAvatarUrl = varchar("added_by_discord_avatar_url", 512).nullable()
}