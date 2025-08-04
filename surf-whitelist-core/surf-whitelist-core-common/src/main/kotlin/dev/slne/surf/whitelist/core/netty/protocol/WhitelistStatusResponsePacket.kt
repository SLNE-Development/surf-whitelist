package dev.slne.surf.whitelist.core.netty.protocol

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.ResponseNettyPacket
import dev.slne.surf.whitelist.api.common.player.WhitelistStatus
import kotlinx.serialization.Serializable

@SurfNettyPacket("whitelist:bidirectional:whitelist_status", PacketFlow.BIDIRECTIONAL)
@Serializable
class WhitelistStatusResponsePacket(val status: WhitelistStatus) : ResponseNettyPacket()