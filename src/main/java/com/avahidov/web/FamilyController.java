package com.avahidov.web;

import com.avahidov.service.FamilyPersonService;
import com.avahidov.web.dto.FamilyPersonDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
