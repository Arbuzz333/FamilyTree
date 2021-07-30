package com.reactiver2dbc.avahidov.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain


@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun userDetailsService(): MapReactiveUserDetailsService {
        val user: UserDetails = User
            .withUsername("user")
            .password(passwordEncoder().encode("userpwd"))
            .roles("USER")
            .build()
        val admin: UserDetails = User
            .withUsername("admin")
            .password(passwordEncoder().encode("adminpwd"))
            .roles("ADMIN")
            .build()
        return MapReactiveUserDetailsService(user, admin)
    }


    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        http.csrf().disable()
            .authorizeExchange()
            .pathMatchers("/persons/list")
            .hasAnyRole("USER", "ADMIN")
            .pathMatchers("/persons/**")
            .hasRole("ADMIN")
            .anyExchange().authenticated()
            .and()
            .httpBasic(withDefaults())
            .formLogin(withDefaults())
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}