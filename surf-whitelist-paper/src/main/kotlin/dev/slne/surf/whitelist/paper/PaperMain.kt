package dev.slne.surf.whitelist.paper

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import org.bukkit.plugin.java.JavaPlugin

class PaperMain : SuspendingJavaPlugin() {
}

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)