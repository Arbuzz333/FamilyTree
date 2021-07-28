package com.reactiver2dbc.avahidov.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table(value = "person_family")
class PersonEntity(
    passport: Long, name: String?, secondName: String?, live: Boolean?
) {
    @Id
    @Column(value = "passport")
    var passport: Long = -1

    @Column(value = "name")
    var name: String = ""

    @Column(value = "second_name")
    var secondName: String = ""

    @Column(value = "live")
    var live: Boolean = true

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersonEntity

        if (passport != other.passport) return false
        if (name != other.name) return false
        if (secondName != other.secondName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = passport.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + secondName.hashCode()
        return result
    }

}