package com.avahidov.service;

import com.avahidov.entity.PersonFamilyEntity;
import com.avahidov.repository.PersonFamilyEntityRepository;
import com.avahidov.web.dto.FamilyPersonDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class FamilyPersonServiceImpl implements FamilyPersonService {

    private final PersonFamilyEntityRepository repository;

    public FamilyPersonServiceImpl(PersonFamilyEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public FamilyPersonDto getPersonTreeDto(long id) {
        FamilyPersonDto dto = new FamilyPersonDto();
        Optional<PersonFamilyEntity> optional = repository.findById(id);

        if (optional.isPresent()) {
            PersonFamilyEntity person = optional.get();
            Integer[] parent = person.getParent();
            if (parent != null) {
                List<Long> longs = Arrays.stream(parent)
                        .map(Long::valueOf)
                        .collect(Collectors.toList());
                List<PersonFamilyEntity> entityParentList = repository.findAllById(longs);
                if (!entityParentList.isEmpty()) {
                    List<FamilyPersonDto> listParent = entityParentList.stream()
                            .map(this::mapFromEntity)
                            .collect(Collectors.toList());
                    dto.setParent(listParent);
                }
            }
        }
        return dto;
    }

    private FamilyPersonDto mapFromEntity(PersonFamilyEntity entity) {
        FamilyPersonDto dto = new FamilyPersonDto(entity.getPassport(), entity.getName(), entity.getSecondName(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        return dto;
    }
}
