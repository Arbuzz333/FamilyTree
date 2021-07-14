package com.avahidov.repository

import com.avahidov.entity.PersonFamilyEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import java.util.Optional

@Repository
interface PersonFamilyEntityRepository: JpaRepository<PersonFamilyEntity, Long> {

    fun findByPassportAndLiveTrue(passport: Long): Optional<PersonFamilyEntity>

    fun findAllByPassportInAndLiveTrue(passport: List<Long>): List<PersonFamilyEntity>
}
