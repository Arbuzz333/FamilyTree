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
    }

    @Test
    fun updatePerson() {
    }

    @Test
    fun getService() {
    }

}
