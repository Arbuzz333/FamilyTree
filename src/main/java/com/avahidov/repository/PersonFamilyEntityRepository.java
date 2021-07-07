package com.avahidov.repository;

import com.avahidov.entity.PersonFamilyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonFamilyEntityRepository extends JpaRepository<PersonFamilyEntity, Long> {

    Optional<PersonFamilyEntity> findByPassportAndLiveTrue(Long passport);

    List<PersonFamilyEntity> findAllByPassportInAndLiveTrue(List<Long> passport);
}
