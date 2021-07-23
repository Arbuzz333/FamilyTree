package com.redis.avahidov.web

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.net.URL


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerTest(
    @Autowired
    val template: TestRestTemplate, @LocalServerPort val port: Int) {

    lateinit var base: URL

    @BeforeEach
    fun setUp(){
        this.base = URL("http://localhost:$port/avahidov-redis/person")
    }

    @Test
    fun getPersonList() {
        val responseEntity = template.getForEntity("$base/list/100", List::class.java)
        val list = responseEntity.body
        assertThat(list?.isNotEmpty()).isTrue
    }

    @Test
    fun addPersonAndGetPerson() {
        val s = System.currentTimeMillis().toString()
        val m = s.substring(s.length - 3, s.length)
        val dto = PersonDto(30003000 + m.toLong(), "NameFirst$m", "NameSecond$m", true)
        val statusEntity = template.postForEntity("$base/add", dto, HttpStatus::class.java)
        assertThat(statusEntity.statusCode).isEqualTo(HttpStatus.CREATED)

        val responseEntity = template.getForEntity("$base/${dto.passport}", PersonDto::class.java)
        val body = responseEntity.body
        assertThat(body).isEqualTo(dto)
    }

    @Test
    fun deletePerson() {
        val s = System.currentTimeMillis().toString()
        val m = s.substring(s.length - 3, s.length)
        val dto = PersonDto(30003000 + m.toLong(), "NameFirst$m", "NameSecond$m", true)
        val statusEntity = template.postForEntity("$base/add", dto, HttpStatus::class.java)
        assertThat(statusEntity.statusCode).isEqualTo(HttpStatus.CREATED)

        val responseEntity = template.exchange("$base/delete", HttpMethod.DELETE, HttpEntity(dto.passport), HttpStatus::class.java)
        val statusCode = responseEntity.statusCode
        assertThat(statusCode).isEqualTo(HttpStatus.OK)

        val responseGet = template.getForEntity("$base/${dto.passport}", PersonDto::class.java)
        val body = responseGet.body
        assertThat(body?.passport).isEqualTo(-1)
    }

    @Test
    fun updatePerson() {
        val s = System.currentTimeMillis().toString()
        val m = s.substring(s.length - 3, s.length)
        val dto = PersonDto(30003000 + m.toLong(), "NameFirst$m", "NameSecond$m", true)
        val statusEntity = template.postForEntity("$base/add", dto, HttpStatus::class.java)
        assertThat(statusEntity.statusCode).isEqualTo(HttpStatus.CREATED)

        val dtoUp = PersonDto(dto.passport, dto.secondName + "Up", dto.secondName + "Up", dto.live)
        val response = template.exchange("$base/update", HttpMethod.PUT, HttpEntity(dtoUp), HttpStatus::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)

        val responseGet = template.getForEntity("$base/${dto.passport}", PersonDto::class.java)
        val body = responseGet.body
        assertThat(body).isEqualTo(dtoUp)
    }

}
