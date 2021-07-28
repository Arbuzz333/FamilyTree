package com.reactiver2dbc.avahidov.web

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.data.util.ProxyUtils
import java.util.ArrayList
import java.util.Objects
import java.util.TreeSet


@JsonInclude(JsonInclude.Include.NON_EMPTY)
class PersonDto(val passport: Long, val name: String?, val secondName: String?, live: Boolean): Comparable<PersonDto> {

    var live: Boolean? = null

    override fun
    equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false
        other as PersonDto
        return passport == other.passport && name == other.name && secondName == other.secondName
    }

    override fun
    hashCode(): Int {
        return Objects.hash(passport, name, secondName)
    }

    override fun
    compareTo(other: PersonDto): Int {
        return this.passport.compareTo(other.passport)
    }
}
