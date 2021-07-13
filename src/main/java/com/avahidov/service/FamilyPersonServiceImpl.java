package com.avahidov.service;

import com.avahidov.entity.PersonFamilyEntity;
import com.avahidov.exception.NotFoundPersonException;
import com.avahidov.repository.PersonFamilyEntityRepository;
import com.avahidov.web.dto.FamilyPersonDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


@Service
public class FamilyPersonServiceImpl implements FamilyPersonService {

    private final PersonFamilyEntityRepository repository;

    public FamilyPersonServiceImpl(PersonFamilyEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public FamilyPersonDto getPersonTreeDto(long id) {
        FamilyPersonDto dto = new FamilyPersonDto(id, "", "");
        Optional<PersonFamilyEntity> optional = repository.findByPassportAndLiveTrue(id);

        if (optional.isPresent()) {
            PersonFamilyEntity person = optional.get();
            dto = mapFromEntity(person);
            long[] parents = person.getParent();
            if (parents != null) {
                findParent(parents, dto);
                TreeSet<FamilyPersonDto> sisterBrotherDto = findSisterBrother(parents, person.getPassport());
                dto.setSisterBrother(sisterBrotherDto);
            }
            long[] children = person.getChildren();
            if (children != null) {
                findChildren(children, dto);
            }
        }
        return dto;
    }

    @Override
    @Transactional
    public void addPerson(FamilyPersonDto dto) {
        PersonFamilyEntity entity = new PersonFamilyEntity();
        recordEntity(dto, entity);
        repository.save(entity);
    }

    @Override
    @Transactional
    public void deletePerson(Long passport) {
        repository.deleteById(passport);
    }

    @Override
    @Transactional
    public void updatePerson(FamilyPersonDto dto) {
        Optional<PersonFamilyEntity> optional = repository.findById(dto.getPassport());
        if (optional.isPresent()) {
            var entity = optional.get();
            recordEntity(dto, entity);
        } else {
            throw new NotFoundPersonException(String.valueOf(dto.getPassport()));
        }
    }

    private void recordEntity(FamilyPersonDto dto, PersonFamilyEntity entity) {
        entity.setPassport(dto.getPassport());
        entity.setName(dto.getName());
        entity.setSecondName(dto.getSecondName());
        entity.setLive(dto.getLive());
        List<FamilyPersonDto> parentList = dto.getParent();
        if (parentList != null && !parentList.isEmpty()) {
            long[] longs = parentList.stream()
                    .map(FamilyPersonDto::getPassport)
                    .mapToLong(p -> p)
                    .toArray();
            entity.setParent(longs);
        }
        List<FamilyPersonDto> childrenList = dto.getChildren();
        if (childrenList != null && !childrenList.isEmpty()) {
            long[] longs = childrenList.stream()
                    .map(FamilyPersonDto::getPassport)
                    .mapToLong(p -> p)
                    .toArray();
            entity.setChildren(longs);
        }
    }

    private void findParent(long[] parents, FamilyPersonDto dto) {
        List<Long> longs = LongStream.of(parents)
                .boxed()
                .collect(Collectors.toList());
        List<PersonFamilyEntity> entityParentList = repository.findAllByPassportInAndLiveTrue(longs);
        for (PersonFamilyEntity entity : entityParentList) {
            FamilyPersonDto parentDto = mapFromEntity(entity);
            dto.getParent().add(parentDto);
            long[] parentGrand = entity.getParent();
            if (parentGrand != null) {
                findParent(parentGrand, parentDto);
            }
        }
    }

    private void findChildren(long[] children, FamilyPersonDto dto) {
        List<Long> longs = LongStream.of(children)
                .boxed()
                .collect(Collectors.toList());
        List<PersonFamilyEntity> entityChildrenList = repository.findAllByPassportInAndLiveTrue(longs);
        for (PersonFamilyEntity entity : entityChildrenList) {
            FamilyPersonDto parentDto = mapFromEntity(entity);
            dto.getChildren().add(parentDto);
            long[] childGrand = entity.getChildren();
            if (childGrand != null) {
                findChildren(childGrand, parentDto);
            }
        }
    }

    private TreeSet<FamilyPersonDto> findSisterBrother(long[] parents, Long passport) {
        TreeSet<FamilyPersonDto> childrenDto = new TreeSet<>();
        for (Long idParent : parents) {
            Optional<PersonFamilyEntity> optionalParent = repository.findByPassportAndLiveTrue(idParent);
            if (optionalParent.isPresent()) {
                PersonFamilyEntity entityParent = optionalParent.get();
                long[] childrenArray = entityParent.getChildren();
                List<Long> longs = Arrays.stream(childrenArray)
                        .filter(l -> l != passport)
                        .boxed()
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
        FamilyPersonDto dto = new FamilyPersonDto(entity.getPassport(), entity.getName(), entity.getSecondName());
        return dto;
    }
}
