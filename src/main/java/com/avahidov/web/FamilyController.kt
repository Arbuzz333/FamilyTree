package com.avahidov.web

import com.avahidov.service.FamilyPersonService
import com.avahidov.web.dto.FamilyPersonDto
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
@RequestMapping("/family-tree")
class FamilyController(
    val service: FamilyPersonService) {

    @GetMapping("/{passport}")
    fun getPersonTree(@PathVariable passport: String ): FamilyPersonDto {
        return service.getPersonTreeDto(passport.toLong())
    }

    @PostMapping("/add-person")
    @ResponseStatus(HttpStatus.CREATED)
    fun addPerson(@Valid @RequestBody dto: FamilyPersonDto) {
        service.addPerson(dto)
    }

    @DeleteMapping("/delete-person")
    @ResponseStatus(HttpStatus.OK)
    fun deletePerson(@NotNull @RequestBody passport: Long) {
        service.deletePerson(passport)
    }

    @PutMapping("/update-person")
    @ResponseStatus(HttpStatus.OK)
    fun updatePerson(@Valid @RequestBody dto: FamilyPersonDto) {
        service.updatePerson(dto)
    }
}
