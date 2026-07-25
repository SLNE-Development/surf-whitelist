package dev.slne.surf.freebuild.whitelist.redis

import com.github.benmanes.caffeine.cache.Caffeine
import com.sksamuel.aedile.core.asLoadingCache
import com.sksamuel.aedile.core.expireAfterWrite
import dev.slne.surf.freebuild.whitelist.plugin
import dev.slne.surf.freebuild.whitelist.redis.request.DiscordMemberShipRequest
import dev.slne.surf.freebuild.whitelist.redis.request.DiscordMemberShipResponse
import kotlin.time.Duration.Companion.minutes

object RedisDiscordService {
    private val resultCache = Caffeine.newBuilder()
        .expireAfterWrite(5.minutes)
        .asLoadingCache<Long, Boolean> { discordId ->
            redisApi.sendRequest<DiscordMemberShipResponse>(DiscordMemberShipRequest(discordId)).isMember
        }

    suspend fun isDiscordMember(discordUserId: Long) = runCatching {
        resultCache.get(discordUserId)
    }.onFailure {
        plugin.logger.severe("Failed to request Discord membership for user $discordUserId: ${it.message}")
    }.getOrDefault(false)
}