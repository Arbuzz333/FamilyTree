package com.redis.avahidov.service

import com.redis.avahidov.web.PersonDto


interface PersonService {

    fun getPersonDto(passport: Long): PersonDto
    fun getPersonDtoList(limit: Int): List<PersonDto>
    fun addPerson(dto: PersonDto)
    fun deletePerson(passport: Long)
    fun updatePerson(dto: PersonDto)

}
