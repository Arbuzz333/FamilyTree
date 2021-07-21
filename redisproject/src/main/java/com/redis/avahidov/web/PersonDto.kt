package com.redis.avahidov.web

import java.io.Serializable


data class PersonDto(val passport: Long, val name: String, val secondName: String, val live: Boolean): Serializable
