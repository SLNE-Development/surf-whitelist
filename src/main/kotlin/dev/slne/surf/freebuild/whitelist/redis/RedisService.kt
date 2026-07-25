package dev.slne.surf.freebuild.whitelist.redis

import dev.slne.surf.redis.RedisApi

object RedisService {
    lateinit var redisApi: RedisApi

    fun connect() {
        redisApi = RedisApi.create()

        redisApi.freezeAndConnect()
    }

    fun disconnect() {
        if (::redisApi.isInitialized && redisApi.isConnected()) {
            redisApi.disconnect()
        }
    }
}

val redisApi get() = RedisService.redisApi