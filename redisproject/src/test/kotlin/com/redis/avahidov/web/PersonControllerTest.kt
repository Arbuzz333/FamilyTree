package com.redis.avahidov.web

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
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
    fun getPerson() {
        val responseEntity = template.getForEntity("$base/30003000", PersonDto::class.java)
        val body = responseEntity.body
        val dto = PersonDto(30003000, "NameFirst", "NameSecond", true)
        assertThat(body).isEqualTo(dto)
    }

    @Test
    fun addPerson() {
        val dto = PersonDto(30003000, "NameFirst", "NameSecond", true)
        val statusEntity = template.postForEntity("$base/add", dto, HttpStatus::class.java)
        assertThat(statusEntity.statusCode).isEqualTo(HttpStatus.CREATED)
    }

    @Test
    fun deletePerson() {
    }

    @Test
    fun updatePerson() {
    }

    @Test
    fun getService() {
    }

}
