package dev.slne.surf.whitelist.server

import dev.slne.surf.cloud.api.server.exposed.migration.generateSimpleExposedMigration
import dev.slne.surf.whitelist.server.db.WhitelistTable

fun main() {

    generateSimpleExposedMigration(
        WhitelistTable,
        scriptName = "V1__initial_whitelist_table",
    )
}