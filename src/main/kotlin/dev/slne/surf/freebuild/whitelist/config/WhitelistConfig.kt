package dev.slne.surf.freebuild.whitelist.config

import dev.slne.surf.api.core.config.SpongeYmlConfigClass
import dev.slne.surf.freebuild.whitelist.plugin
import org.spongepowered.configurate.objectmapping.ConfigSerializable

@ConfigSerializable
data class WhitelistConfig(
    var whitelistEnabled: Boolean = true
) {
    companion object : SpongeYmlConfigClass<WhitelistConfig>(
        WhitelistConfig::class.java,
        plugin.dataPath,
        "config.yml"
    )
}
