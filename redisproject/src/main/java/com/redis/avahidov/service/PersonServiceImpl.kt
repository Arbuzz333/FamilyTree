package com.redis.avahidov.service

import com.redis.avahidov.model.Person
import com.redis.avahidov.repository.PersonRepository
import com.redis.avahidov.web.PersonDto
import org.springframework.stereotype.Service


@Service
class PersonServiceImpl(
    val repository: PersonRepository
) : PersonService {

    override fun getPersonDto(passport: Long): PersonDto {
        val person = repository.findPerson(passport)
        return PersonDto(person.passport, person.name, person.secondName, person.live)
    }

    override fun addPerson(dto: PersonDto) {
        repository.add(Person(dto.passport, dto.name, dto.secondName, dto.live))
    }

    override fun deletePerson(passport: Long) {
        repository.delete(passport)
    }

    override fun updatePerson(dto: PersonDto) {
        repository.update(Person(dto.passport, dto.name, dto.secondName, dto.live))
    }
}
