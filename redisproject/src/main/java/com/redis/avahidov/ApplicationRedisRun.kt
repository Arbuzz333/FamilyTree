package com.redis.avahidov

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories


@SpringBootApplication
@EnableRedisRepositories
class ApplicationRedisRun

    fun main(args: Array<String>) {
        runApplication<ApplicationRedisRun>(*args)
    }
