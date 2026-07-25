package dev.slne.surf.freebuild.whitelist.database

import dev.slne.surf.database.DatabaseApi
import java.nio.file.Path

object DatabaseService {
    lateinit var databaseApi: DatabaseApi

    fun connect(dataPath: Path) {
        databaseApi = DatabaseApi.create(dataPath)
    }

    fun disconnect() {
        databaseApi.shutdown()
    }
}