package dev.slne.surf.freebuild.whitelist

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.freebuild.whitelist.command.freebuildWhitelistCommand
import dev.slne.surf.freebuild.whitelist.database.databaseLoader
import dev.slne.surf.freebuild.whitelist.listener.PlayerLoginListener
import dev.slne.surf.api.paper.event.register
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : SuspendingJavaPlugin() {
    override suspend fun onEnableAsync() {
        PlayerLoginListener.register()

        databaseLoader.connect(plugin.dataPath)
        freebuildWhitelistCommand()
    }

    override suspend fun onDisableAsync() {
        databaseLoader.disconnect()
    }
}
