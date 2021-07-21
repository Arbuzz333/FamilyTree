package com.redis.avahidov.model

import java.io.Serializable


data class Person(val passport: Long, val name: String, val secondName: String, val live: Boolean): Serializable
