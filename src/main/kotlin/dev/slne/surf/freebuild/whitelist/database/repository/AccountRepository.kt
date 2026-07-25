package dev.slne.surf.freebuild.whitelist.database.repository

import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.and
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.core.eq
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.select
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import dev.slne.surf.freebuild.whitelist.database.table.AccountsTable
import kotlinx.coroutines.flow.firstOrNull
import java.util.*

object AccountRepository {
    suspend fun findDiscordAccountByMinecraftUuid(minecraftUuid: UUID) = suspendTransaction {
        val minecraftAccountProviderId = AccountsTable
            .select(AccountsTable.providerAccountId, AccountsTable.provider, AccountsTable.userId)
            .where((AccountsTable.provider eq "minecraft") and (AccountsTable.providerAccountId eq minecraftUuid.toString()))
            .firstOrNull()?.getOrNull(AccountsTable.userId)
            ?: return@suspendTransaction null

        return@suspendTransaction AccountsTable
            .select(AccountsTable.providerAccountId, AccountsTable.provider, AccountsTable.userId)
            .where((AccountsTable.provider eq "discord") and (AccountsTable.userId eq minecraftAccountProviderId))
            .firstOrNull()?.getOrNull(AccountsTable.providerAccountId)?.toLongOrNull()
    }
}