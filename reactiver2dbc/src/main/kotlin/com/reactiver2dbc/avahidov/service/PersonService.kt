package com.reactiver2dbc.avahidov.service

import com.reactiver2dbc.avahidov.repository.PersonEntity
import com.reactiver2dbc.avahidov.repository.PersonRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class PersonService(
    val personRepository: PersonRepository
) {

    fun getPersonList(limit: Long): Flux<PersonEntity> {
        return personRepository.findAllLimit(limit)
    }

    fun findPerson(passport: Long): Mono<PersonEntity> {
        return personRepository.findById(passport)
    }

    fun addPerson(person: PersonEntity): Mono<Long> {
        return personRepository.save(person)
            .map {  p -> p.passport }
    }

    fun updatePerson(person: PersonEntity): Mono<Long> {
        return personRepository.findById(person.passport)
            .flatMap { s ->
                person.passport = s.passport
                personRepository.save(person)
                    .map { p -> p.passport }
            }
    }

    fun deletePerson(passport: Long): Mono<Void> {
        return personRepository.deleteById(passport)
    }
}