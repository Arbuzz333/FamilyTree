package com.redis.avahidov.repository

import com.redis.avahidov.model.Person


interface PersonRepository {

    fun findAllPerson(limit: Int): Set<Person>

    fun add(person: Person)

    fun delete(passport: Long)

    fun findPerson(passport: Long): Person

    fun update(person: Person)
}
