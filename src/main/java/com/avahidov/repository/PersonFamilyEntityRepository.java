package com.avahidov.repository;

import com.avahidov.entity.PersonFamilyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonFamilyEntityRepository extends JpaRepository<PersonFamilyEntity, Long> {
}
