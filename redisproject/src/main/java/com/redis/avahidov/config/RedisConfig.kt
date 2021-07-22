package com.redis.avahidov.config

import com.redis.avahidov.model.Person
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer


@Configuration
class RedisConfig {

    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory {
        return JedisConnectionFactory()
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(jedisConnectionFactory())

        template.valueSerializer = Jackson2JsonRedisSerializer(Person::class.java)
        template.hashValueSerializer = Jackson2JsonRedisSerializer(Person::class.java)
        return template
    }

}
