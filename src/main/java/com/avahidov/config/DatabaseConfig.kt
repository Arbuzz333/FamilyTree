package com.avahidov.config


import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import javax.sql.DataSource


@Configuration
 class DatabaseConfig(
    val connectionSettings: ConnectionSettings) {

    @Bean
    fun dataSourceHikari(): DataSource {
        val hikariConfig = HikariConfig()
        hikariConfig.driverClassName = connectionSettings.jdbcDriver
        hikariConfig.jdbcUrl = connectionSettings.jdbcString
        hikariConfig.username = connectionSettings.jdbcUser
        hikariConfig.password = connectionSettings.jdbcPassword
        hikariConfig.maximumPoolSize = connectionSettings.jdbcMaxPoolSize
        hikariConfig.poolName = "main"
        return HikariDataSource(hikariConfig)
    }
}
