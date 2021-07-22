package com.redis.avahidov.web

import com.redis.avahidov.service.PersonService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid
import javax.validation.constraints.NotNull


@RestController
@RequestMapping("/person")
class PersonController(
    val service: PersonService
) {

    @GetMapping("/{passport}")
    fun getPerson(@PathVariable passport: String ): PersonDto {
        return service.getPersonDto(passport.toLong())
    }

    @GetMapping("/list/{limit}")
    fun getPersonList(@PathVariable limit: String ): List<PersonDto> {
        return service.getPersonDtoList(limit.toInt())
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    fun addPerson(@Valid @RequestBody dto: PersonDto) {
        service.addPerson(dto)
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    fun deletePerson(@NotNull @RequestBody passport: Long) {
        service.deletePerson(passport)
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    fun updatePerson(@Valid @RequestBody dto: PersonDto) {
        service.updatePerson(dto)
    }
}
