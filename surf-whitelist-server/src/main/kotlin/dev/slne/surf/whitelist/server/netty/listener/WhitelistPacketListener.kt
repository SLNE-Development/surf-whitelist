package dev.slne.surf.whitelist.server.netty.listener

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacketHandler
import dev.slne.surf.whitelist.core.netty.protocol.ServerboundCreateWhitelistPacket
import dev.slne.surf.whitelist.core.netty.protocol.ServerboundFetchWhitelistPacket
import dev.slne.surf.whitelist.core.netty.protocol.ServerboundIsWhitelistedPacket
import dev.slne.surf.whitelist.core.netty.protocol.WhitelistResponsePacket
import dev.slne.surf.whitelist.core.netty.protocol.WhitelistStatusResponsePacket
import dev.slne.surf.whitelist.server.whitelist.WhitelistService
import org.springframework.stereotype.Component

@Suppress("unused")
@Component
class WhitelistPacketListener(private val whitelistService: WhitelistService) {

    @SurfNettyPacketHandler
    suspend fun handleCreateWhitelist(packet: ServerboundCreateWhitelistPacket) {
        val whitelist = whitelistService.createWhitelist(packet.whitelist)
        packet.respond(WhitelistResponsePacket(whitelist))
    }

    @SurfNettyPacketHandler
    suspend fun handleFetchWhitelist(packet: ServerboundFetchWhitelistPacket) {
        val whitelist = whitelistService.getWhitelist(packet.player)
        packet.respond(WhitelistResponsePacket(whitelist))
    }

    suspend fun handleIsWhitelisted(packet: ServerboundIsWhitelistedPacket) {
        val status = whitelistService.isWhitelisted(packet.player)
        packet.respond(WhitelistStatusResponsePacket(status))
    }
}