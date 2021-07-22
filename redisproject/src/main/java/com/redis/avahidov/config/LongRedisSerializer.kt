package com.redis.avahidov.config

import org.springframework.data.redis.serializer.RedisSerializer
import java.nio.ByteBuffer


class LongRedisSerializer: RedisSerializer<Long> {

    override fun serialize(t: Long?): ByteArray? {
        return if (t != null) {
            val buffer = ByteBuffer.allocate(Long.SIZE_BYTES)
            buffer.putLong(t)
            return buffer.array()
        } else null
    }

    override fun deserialize(bytes: ByteArray?): Long? {
        return if (bytes != null) {
            val buffer = ByteBuffer.allocate(Long.SIZE_BYTES)
            buffer.put(bytes)
            buffer.flip()
            buffer.long
        } else null
    }
}