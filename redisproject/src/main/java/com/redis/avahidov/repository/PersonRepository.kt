package com.redis.avahidov.repository

import com.redis.avahidov.model.Person


interface PersonRepository {

    fun findAllPerson(): MutableMap<Long, Person>

    fun add(person: Person)

    fun delete(passport: Long)

    fun findPerson(passport: Long): Person

    fun update(person: Person)
}
