package dev.slne.surf.whitelist.core.netty.protocol

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.whitelist.core.WhitelistEntryImpl
import kotlinx.serialization.Serializable

@SurfNettyPacket("whitelist:serverbound:create_whitelist", PacketFlow.SERVERBOUND)
@Serializable
class ServerboundCreateWhitelistPacket(val whitelist: WhitelistEntryImpl) :
    RespondingNettyPacket<WhitelistResponsePacket>()