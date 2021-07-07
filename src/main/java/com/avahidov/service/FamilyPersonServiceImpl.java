package com.avahidov.service;

import com.avahidov.entity.PersonFamilyEntity;
import com.avahidov.repository.PersonFamilyEntityRepository;
import com.avahidov.web.dto.FamilyPersonDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
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
        Optional<PersonFamilyEntity> optional = repository.findByPassportAndLiveTrue(id);

        if (optional.isPresent()) {
            PersonFamilyEntity person = optional.get();
            dto = mapFromEntity(person);
            Long[] parents = person.getParent();
            if (parents != null) {
                findParent(parents, dto);
                Set<FamilyPersonDto> sisterBrotherDto = findSisterBrother(parents, person.getPassport());
                dto.setSisterBrother(sisterBrotherDto);
            }
            Long[] children = person.getChildren();
            if (children != null) {
                findChildren(children, dto);
            }
        }
        return dto;
    }

    private void findParent(Long[] parents, FamilyPersonDto dto) {
        List<Long> longs = Arrays.stream(parents)
                .collect(Collectors.toList());
        List<PersonFamilyEntity> entityParentList = repository.findAllByPassportInAndLiveTrue(longs);
        for (PersonFamilyEntity entity : entityParentList) {
            FamilyPersonDto parentDto = mapFromEntity(entity);
            dto.getParent().add(parentDto);
            Long[] parentGrand = entity.getParent();
            if (parentGrand != null) {
                findParent(parentGrand, parentDto);
            }
        }
    }

    private void findChildren(Long[] children, FamilyPersonDto dto) {
        List<Long> longs = Arrays.stream(children)
                .collect(Collectors.toList());
        List<PersonFamilyEntity> entityChildrenList = repository.findAllByPassportInAndLiveTrue(longs);
        for (PersonFamilyEntity entity : entityChildrenList) {
            FamilyPersonDto parentDto = mapFromEntity(entity);
            dto.getChildren().add(parentDto);
            Long[] childGrand = entity.getChildren();
            if (childGrand != null) {
                findChildren(childGrand, parentDto);
            }
        }
    }

    private Set<FamilyPersonDto> findSisterBrother(Long[] parents, Long passport) {
        Set<FamilyPersonDto> childrenDto = new TreeSet<>();
        for (Long idParent : parents) {
            Optional<PersonFamilyEntity> optionalParent = repository.findByPassportAndLiveTrue(idParent);
            if (optionalParent.isPresent()) {
                PersonFamilyEntity entityParent = optionalParent.get();
                Long[] childrenArray = entityParent.getChildren();
                List<Long> longs = Arrays.stream(childrenArray)
                        .filter(l -> !l.equals(passport))
                        .collect(Collectors.toList());
                List<PersonFamilyEntity> entityChildList = repository.findAllByPassportInAndLiveTrue(longs);
                Set<FamilyPersonDto> dtoSet = entityChildList.stream()
                        .map(this::mapFromEntity)
                        .collect(Collectors.toSet());
                childrenDto.addAll(dtoSet);
            }
        }
        return childrenDto;
    }

    private FamilyPersonDto mapFromEntity(PersonFamilyEntity entity) {
        FamilyPersonDto dto = new FamilyPersonDto(entity.getPassport(), entity.getName(), entity.getSecondName(),
                new ArrayList<>(), new TreeSet<>(), new ArrayList<>());
        return dto;
    }
}
