package com.avahidov.web;

import com.avahidov.service.FamilyPersonService;
import com.avahidov.web.dto.FamilyPersonDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/family-tree")
public class FamilyController {

    private final FamilyPersonService service;

    public FamilyController(FamilyPersonService service) {
        this.service = service;
    }

    @GetMapping("/{passport}")
    public FamilyPersonDto getPersonTree(@PathVariable String passport) {
        return service.getPersonTreeDto(Long.parseLong(passport));
    }

    @PostMapping("/add-person")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPerson(@Valid @RequestBody FamilyPersonDto dto) {
        service.addPerson(dto);
    }

    @DeleteMapping("/delete-person")
    @ResponseStatus(HttpStatus.OK)
    public void deletePerson(@NotNull @RequestBody Long passport) {
        service.deletePerson(passport);
    }


    @PutMapping("/update-person")
    @ResponseStatus(HttpStatus.OK)
    public void updatePerson(@Valid @RequestBody FamilyPersonDto dto) {
        service.updatePerson(dto);
    }
}
