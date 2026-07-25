package dev.slne.surf.freebuild.whitelist

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.api.paper.event.register
import dev.slne.surf.freebuild.whitelist.command.whitelistToggleCommand
import dev.slne.surf.freebuild.whitelist.database.DatabaseService
import dev.slne.surf.freebuild.whitelist.listener.PlayerAsyncLoginListener
import dev.slne.surf.freebuild.whitelist.redis.RedisService
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : SuspendingJavaPlugin() {
    override suspend fun onEnableAsync() {
        PlayerAsyncLoginListener.register()

        DatabaseService.connect(plugin.dataPath)
        RedisService.connect()

        whitelistToggleCommand()
    }

    override suspend fun onDisableAsync() {
        DatabaseService.disconnect()
        RedisService.disconnect()
    }
}
