package com.redis.avahidov.model

import java.io.Serializable


class Person : Serializable, Comparable<Person> {

    var passport: Long = 0
    var name: String = ""
    var secondName: String = ""
    var live: Boolean = true

    override fun compareTo(other: Person): Int {
        return this.passport.compareTo(other.passport)
    }
}
