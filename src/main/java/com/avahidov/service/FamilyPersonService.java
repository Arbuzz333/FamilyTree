package com.avahidov.service;

import com.avahidov.web.dto.FamilyPersonDto;


public interface FamilyPersonService {

    FamilyPersonDto getPersonTreeDto(long id);

    void addPerson(FamilyPersonDto dto);

    void deletePerson(Long passport);

    void updatePerson(FamilyPersonDto passport);
}
