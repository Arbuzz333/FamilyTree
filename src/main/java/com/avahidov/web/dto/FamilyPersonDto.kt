package com.avahidov.web.dto

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.data.util.ProxyUtils
import java.util.ArrayList
import java.util.Objects
import java.util.TreeSet


@JsonInclude(JsonInclude.Include.NON_EMPTY)
class FamilyPersonDto(val passport: Long, val name: String?, val secondName: String?): Comparable<FamilyPersonDto> {

    var live: Boolean? = null

    var parent: ArrayList<FamilyPersonDto>? = ArrayList()
    var sisterBrother: TreeSet<FamilyPersonDto>? = TreeSet()
    var children: ArrayList<FamilyPersonDto>? = ArrayList()

    override fun
    equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) return true
        if (javaClass != ProxyUtils.getUserClass(other)) return false
        other as FamilyPersonDto
        return passport == other.passport && name == other.name && secondName == other.secondName
    }

    override fun
    hashCode(): Int {
        return Objects.hash(passport, name, secondName)
    }

    override fun
    compareTo(other: FamilyPersonDto): Int {
        return this.passport.compareTo(other.passport)
    }
}
