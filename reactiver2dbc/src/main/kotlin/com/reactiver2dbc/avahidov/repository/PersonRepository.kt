package com.reactiver2dbc.avahidov.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux


@Repository
interface PersonRepository: ReactiveCrudRepository<PersonEntity, Long> {

    @Query(value = "SELECT p FROM PersonEntity  p ORDER BY p.passport LIMIT :limit")
    fun findAllLimit(limit: Long): Flux<PersonEntity>
}