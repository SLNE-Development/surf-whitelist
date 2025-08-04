package dev.slne.surf.whitelist.core.netty.protocol

import dev.slne.surf.cloud.api.common.meta.SurfNettyPacket
import dev.slne.surf.cloud.api.common.netty.network.protocol.PacketFlow
import dev.slne.surf.cloud.api.common.netty.packet.RespondingNettyPacket
import dev.slne.surf.cloud.api.common.player.OfflineCloudPlayer
import kotlinx.serialization.Serializable

@SurfNettyPacket("whitelist:serverbound:is_whitelisted", PacketFlow.SERVERBOUND)
@Serializable
class ServerboundIsWhitelistedPacket(val player: OfflineCloudPlayer) :
    RespondingNettyPacket<WhitelistStatusResponsePacket>()