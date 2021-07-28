package com.reactiver2dbc.avahidov.web

import com.reactiver2dbc.avahidov.repository.PersonEntity
import com.reactiver2dbc.avahidov.service.PersonService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono


@Component
class PersonHandler(
    val personService: PersonService) {

    fun getPerson(serverRequest: ServerRequest): Mono<ServerResponse> {
        val personMono: Mono<PersonEntity> = personService.findPerson(serverRequest.pathVariable("passport").toLong())
        return personMono
            .map { p -> PersonDto(p.passport, p.name, p.secondName, p.live)}
            .flatMap { dto -> ServerResponse
                .ok()
                .body(fromValue(dto))}
            .switchIfEmpty(ServerResponse
                .notFound()
                .build())
    }

    fun getPersonList(serverRequest: ServerRequest): Mono<ServerResponse> {
        val limit: String = serverRequest.queryParam("limit").orElse(null)
        val personList = personService.getPersonList(limit.toLong())
        val dtoList = personList
            .map { p -> PersonDto(p.passport, p.name, p.secondName, p.live) }
            .collectList()
        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(dtoList)
    }

    fun addPerson(serverRequest: ServerRequest): Mono<ServerResponse> {
        val personMono: Mono<PersonDto> = serverRequest.bodyToMono(PersonDto::class.java)
        return personMono
            .map { dto -> PersonEntity(dto.passport, dto.name, dto.secondName, dto.live)}
            .flatMap { p -> ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(personService.addPerson(p), Long::class.java)
        }
    }

    fun updatePerson(serverRequest: ServerRequest): Mono<ServerResponse> {
        val personMono: Mono<PersonDto> = serverRequest.bodyToMono(PersonDto::class.java)
        return personMono
            .map { dto -> PersonEntity(dto.passport, dto.name, dto.secondName, dto.live)}
            .flatMap { p ->
            ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(personService.updatePerson(p), Long::class.java)
                .switchIfEmpty(ServerResponse
                    .notFound()
                    .build())
        }
    }

    fun deletePerson(serverRequest: ServerRequest): Mono<ServerResponse> {
        val passportMono: Mono<Long> = serverRequest.bodyToMono(Long::class.java)
        return passportMono
            .flatMap { s ->
                ServerResponse.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(personService.deletePerson(s), Long::class.java)
                    .switchIfEmpty(ServerResponse
                        .notFound()
                        .build())
            }
    }
}