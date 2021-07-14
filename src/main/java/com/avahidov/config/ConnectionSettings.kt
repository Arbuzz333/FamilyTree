package com.avahidov.config


import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@Component
@ConfigurationProperties(prefix = "mainpool")
class ConnectionSettings {
    private val defaultMaxPoolSize: Int = 5

    lateinit var jdbcDriver: String
    lateinit var jdbcString: String
    lateinit var jdbcUser: String
    lateinit var jdbcPassword: String
    var jdbcMaxPoolSize: Int = defaultMaxPoolSize

}
