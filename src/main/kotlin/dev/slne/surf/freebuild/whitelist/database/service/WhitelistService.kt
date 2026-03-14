package dev.slne.surf.freebuild.whitelist.database.service

import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.ResultRow
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.eq
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.select
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.selectAll
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import dev.slne.surf.freebuild.whitelist.database.table.SocialsTable
import dev.slne.surf.freebuild.whitelist.entry.SimpleWhitelistEntry
import dev.slne.surf.freebuild.whitelist.entry.WhitelistEntry
import kotlinx.coroutines.flow.firstOrNull
import java.util.*

val whitelistService = WhitelistService()

class WhitelistService {
    suspend fun findWhitelist(uuid: UUID) = suspendTransaction {
        SocialsTable.selectAll().where(SocialsTable.minecraftUuid eq uuid).firstOrNull()
            ?.let { row ->
                createWhitelist(row)
            }
    }

    suspend fun findSimpleWhitelist(uuid: UUID) = suspendTransaction {
        SocialsTable.select(SocialsTable.minecraftUuid, SocialsTable.blocked)
            .where(SocialsTable.minecraftUuid eq uuid).firstOrNull()?.let { row ->
                createSimpleWhitelist(row)
            }
    }

    private fun createWhitelist(row: ResultRow) = WhitelistEntry(
        discordUserId = row[SocialsTable.discordUserId],
        minecraftUuid = row[SocialsTable.minecraftUuid],
        twitchId = row[SocialsTable.twitchId],
        blocked = row[SocialsTable.blocked],
        createdAt = row[SocialsTable.createdAt],
        updatedAt = row[SocialsTable.updatedAt]
    )

    private fun createSimpleWhitelist(row: ResultRow) = SimpleWhitelistEntry(
        minecraftUuid = row[SocialsTable.minecraftUuid],
        blocked = row[SocialsTable.blocked]
    )
}