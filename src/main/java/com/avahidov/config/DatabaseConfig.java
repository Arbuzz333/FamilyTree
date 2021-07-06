package com.avahidov.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class DatabaseConfig {

    private final ConnectionSettings connectionSettings;

    @Autowired
    public DatabaseConfig(ConnectionSettings connectionSettings) {
        this.connectionSettings = connectionSettings;
    }

    @Bean
    public DataSource dataSourceHikari() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(connectionSettings.getJdbcDriver());
        hikariConfig.setJdbcUrl(connectionSettings.getJdbcString());
        hikariConfig.setUsername(connectionSettings.getJdbcUser());
        hikariConfig.setPassword(connectionSettings.getJdbcPassword());
        hikariConfig.setMaximumPoolSize(connectionSettings.getJdbcMaxPoolSize());
        hikariConfig.setPoolName("main");
        return new HikariDataSource(hikariConfig);
    }
}
