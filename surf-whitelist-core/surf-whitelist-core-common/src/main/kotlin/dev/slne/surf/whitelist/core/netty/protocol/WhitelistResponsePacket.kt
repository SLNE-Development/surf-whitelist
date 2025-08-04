package dev.slne.surf.whitelist.core.netty.protocol

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.ResponseNettyPacket
import dev.slne.surf.whitelist.core.WhitelistEntryImpl
import kotlinx.serialization.Serializable

@SurfNettyPacket("whitelist:bidirectional:whitelist_response", PacketFlow.BIDIRECTIONAL)
@Serializable
class WhitelistResponsePacket(val entry: WhitelistEntryImpl?) : ResponseNettyPacket()