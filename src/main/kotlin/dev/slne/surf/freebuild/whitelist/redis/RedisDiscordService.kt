package dev.slne.surf.freebuild.whitelist.redis

import dev.slne.surf.freebuild.whitelist.plugin
import dev.slne.surf.freebuild.whitelist.redis.request.DiscordMemberShipRequest
import dev.slne.surf.freebuild.whitelist.redis.request.DiscordMemberShipResponse

object RedisDiscordService {
    suspend fun requestDiscordMembership(discordUserId: Long) = runCatching {
        redisApi.sendRequest<DiscordMemberShipResponse>(DiscordMemberShipRequest(discordUserId)).isMember
    }.onFailure {
        plugin.logger.severe("Failed to request Discord membership for user $discordUserId: ${it.message}")
    }.getOrDefault(false)

}