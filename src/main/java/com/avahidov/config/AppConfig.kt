package com.avahidov.config

import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AppConfig {

    @Bean
    fun webServerFactoryCustomizer(): WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
        val factory = WebServerFactoryCustomizer { factory: ConfigurableServletWebServerFactory ->
            factory.setContextPath("/avahidov")
        }
        return factory
    }

}
