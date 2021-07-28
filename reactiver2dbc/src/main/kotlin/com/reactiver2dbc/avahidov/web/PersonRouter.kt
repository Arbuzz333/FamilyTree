package com.reactiver2dbc.avahidov.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.RequestPredicates.DELETE
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.POST
import org.springframework.web.reactive.function.server.RequestPredicates.PUT
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse


@Configuration
class PersonRouter {

    @Bean
    fun route(studentHandler: PersonHandler): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route(
                GET("/persons/{passport:[0-9]+}")
                    .and(accept(APPLICATION_JSON)), studentHandler::getPerson
            )
            .andRoute(
                GET("/persons/list/{limit}")
                    .and(accept(APPLICATION_JSON)), studentHandler::getPersonList
            )
            .andRoute(
                POST("/persons/add")
                    .and(accept(APPLICATION_JSON)), studentHandler::addPerson
            )
            .andRoute(
                PUT("persons/update")
                    .and(accept(APPLICATION_JSON)), studentHandler::updatePerson
            )
            .andRoute(
                DELETE("/persons/delete")
                    .and(accept(APPLICATION_JSON)), studentHandler::deletePerson
            )
    }
}
