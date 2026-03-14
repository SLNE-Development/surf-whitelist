package dev.slne.surf.freebuild.whitelist.database

import dev.slne.surf.database.DatabaseApi
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.SchemaUtils
import dev.slne.surf.database.libs.org.jetbrains.exposed.v1.r2dbc.transactions.suspendTransaction
import dev.slne.surf.freebuild.whitelist.database.table.SocialsTable
import java.nio.file.Path

val databaseLoader = DatabaseLoader()

class DatabaseLoader {
    lateinit var databaseApi: DatabaseApi

    suspend fun connect(dataPath: Path) {
        databaseApi = DatabaseApi.create(dataPath)

        suspendTransaction {
            SchemaUtils.create(
                SocialsTable
            )
        }
    }

    fun disconnect() {
        databaseApi.shutdown()
    }
}