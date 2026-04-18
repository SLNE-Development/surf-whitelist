package dev.slne.surf.freebuild.whitelist.database.service

import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.ResultRow
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.eq
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.select
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.selectAll
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import dev.slne.surf.freebuild.whitelist.database.table.FreebuildWhitelistTable
import dev.slne.surf.freebuild.whitelist.database.table.SocialConnectionsTable
import dev.slne.surf.freebuild.whitelist.entry.SimpleWhitelistEntry
import dev.slne.surf.freebuild.whitelist.entry.WhitelistEntry
import kotlinx.coroutines.flow.firstOrNull
import java.util.*

val whitelistService = WhitelistService()

class WhitelistService {
    suspend fun findWhitelist(uuid: UUID) = suspendTransaction {
        (SocialConnectionsTable innerJoin FreebuildWhitelistTable)
            .selectAll()
            .where(SocialConnectionsTable.minecraftUuid eq uuid)
            .firstOrNull()
            ?.let { row -> createWhitelist(row) }
    }

    suspend fun findSimpleWhitelist(uuid: UUID) = suspendTransaction {
        (SocialConnectionsTable innerJoin FreebuildWhitelistTable)
            .select(SocialConnectionsTable.minecraftUuid, FreebuildWhitelistTable.blocked)
            .where(SocialConnectionsTable.minecraftUuid eq uuid)
            .firstOrNull()
            ?.let { row -> createSimpleWhitelist(row) }
    }

    private fun createWhitelist(row: ResultRow) = WhitelistEntry(
        discordUserId = row[SocialConnectionsTable.discordUserId],
        minecraftUuid = row[SocialConnectionsTable.minecraftUuid],
        twitchId = row[SocialConnectionsTable.twitchId],
        blocked = row[FreebuildWhitelistTable.blocked],
        createdAt = row[SocialConnectionsTable.createdAt],
        updatedAt = row[SocialConnectionsTable.updatedAt]
    )

    private fun createSimpleWhitelist(row: ResultRow) = SimpleWhitelistEntry(
        minecraftUuid = row[SocialConnectionsTable.minecraftUuid],
        blocked = row[FreebuildWhitelistTable.blocked]
    )
}