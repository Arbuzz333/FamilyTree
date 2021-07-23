package com.redis.avahidov.service

import com.redis.avahidov.model.Person
import com.redis.avahidov.repository.PersonRepository
import com.redis.avahidov.web.PersonDto
import org.springframework.stereotype.Service
import java.util.stream.Collectors


@Service
class PersonServiceImpl(
    val repository: PersonRepository
) : PersonService {

    override fun getPersonDto(passport: Long): PersonDto {
        val person = repository.findPerson(passport)
        return PersonDto(person?.passport ?: -1, person?.name ?: "",
            person?.secondName ?: "", person?.live ?: false)
    }

    override fun getPersonDtoList(limit: Int): List<PersonDto> {
        val list = repository.findAllPerson(limit)
        val listDto = list.stream()
            .map {p -> PersonDto(p.passport, p.name, p.secondName, p.live) }
            .collect(Collectors.toList())
        return listDto
    }

    override fun addPerson(dto: PersonDto) {
        val person = Person()
        person.passport = dto.passport
        person.name = dto.name
        person.secondName = dto.secondName
        person.live = dto.live
        repository.add(person)
    }

    override fun deletePerson(passport: Long) {
        repository.delete(passport)
    }

    override fun updatePerson(dto: PersonDto) {
        val person = Person()
        person.passport = dto.passport
        person.name = dto.name
        person.secondName = dto.secondName
        person.live = dto.live
        repository.update(person)
    }
}
