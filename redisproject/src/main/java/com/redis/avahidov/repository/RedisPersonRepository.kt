package com.redis.avahidov.repository

import com.redis.avahidov.model.Person
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import javax.annotation.PostConstruct


@Repository
class RedisPersonRepository(
    val redisTemplate: RedisTemplate<String, Any>): PersonRepository {

    private val KEY = "Person"

    private lateinit var hashOperations: HashOperations<String, Long, Person>

    @PostConstruct
    private fun init() {
        hashOperations = redisTemplate.opsForHash()
    }

    override fun add(person: Person) {
        hashOperations.put(KEY, person.passport, person)
    }

    override fun delete(passport: Long) {
        hashOperations.delete(KEY, passport)
    }

    override fun findPerson(passport: Long): Person {
        return hashOperations.get(KEY, passport) as Person
    }

    override fun update(person: Person) {
        add(person)
    }

    override fun findAllPerson(): MutableMap<Long, Person> {
        return hashOperations.entries(KEY)
    }
}
