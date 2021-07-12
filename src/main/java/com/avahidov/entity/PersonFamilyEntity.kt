package com.avahidov.entity

import org.hibernate.annotations.Type
import org.springframework.data.util.ProxyUtils

import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import java.util.Objects

@Entity
@Table(name = "person_family")
class PersonFamilyEntity (

    @Id
    @Column(name = "passport")
    val passport: Long,
    @Basic
    @Column(name = "name")
    val name: String,
    @Basic
    @Column(name = "second_name")
    val secondName: String,
    @Basic
    @Column(name = "parent", columnDefinition = "int[]")
    @Type(type = "com.avahidov.customtype.CustomIntegerArrayType")
    val parent: LongArray,
    @Basic
    @Column(name = "children", columnDefinition = "int[]")
    @Type(type = "com.avahidov.customtype.CustomIntegerArrayType")
    val children: LongArray,
    @Basic
    @Column(name = "live")
    val live: Boolean

) {

    override fun equals(other: Any?): Boolean  {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false
        other as PersonFamilyEntity
        return this.passport == other.passport && this.name == other.name && this.secondName == other.secondName
                && this.live == other.live
    }

    override
    fun hashCode(): Int {
        return Objects.hash(passport)
    }
}
