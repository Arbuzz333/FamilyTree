package com.avahidov.web;

import com.avahidov.web.dto.FamilyPersonDto
import com.fasterxml.jackson.databind.ObjectMapper
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
class FamilyControllerTest(
    @Autowired val template: TestRestTemplate, @LocalServerPort val port: Int) {

    lateinit var base: URL

    @BeforeEach
    fun setUp(){
        this.base = URL("http://localhost:$port/avahidov/family-tree")
    }

    @Test
    fun getPersonTree() {
        val responseEntity = template.getForEntity("$base/10001000", FamilyPersonDto::class.java)
        val body = responseEntity.body
        val objectMapper = ObjectMapper()
        val actual = objectMapper.writeValueAsString(body)
        assertThat(actual).isEqualTo("{\"parent\":[{\"parent\":[{\"passport\":10001011,\"name\":\"Alrksandra\",\"secondName\":\"Timurovna\"}],\"passport\":10001001,\"name\":\"Lev\",\"secondName\":\"Dymich\"},{\"passport\":20001001,\"name\":\"Lena\",\"secondName\":\"Aleksandrovna\"}],\"sisterBrother\":[{\"passport\":20001000,\"name\":\"Denis\",\"secondName\":\"Ivanovich\"}],\"children\":[{\"children\":[{\"passport\":10111000,\"name\":\"Anton\",\"secondName\":\"Alekseych\"}],\"passport\":10011000,\"name\":\"Aleksey\",\"secondName\":\"Ivanovich\"},{\"passport\":10021000,\"name\":\"Andrey\",\"secondName\":\"Ivanovich\"}],\"passport\":10001000,\"name\":\"Ivanov\",\"secondName\":\"Ivanovich\"}");
    }

    @Test
    fun addUpdateDeletePersonTree() {
        val dtoParent1 = FamilyPersonDto(50004000L, "", "")
        val dtoParent2 = FamilyPersonDto(50004001L, "", "")
        val dtoParent3 = FamilyPersonDto(50004002L, "", "")

        val dtoChild1 = FamilyPersonDto(50005001L, "", "")
        val dtoChild2 = FamilyPersonDto(50005002L, "", "")
        val dtoChild3 = FamilyPersonDto(50005003L, "", "")
        val dto = FamilyPersonDto(50005000L, "Tom", "Thomson")
        dto.parent =  ArrayList(listOf(dtoParent1, dtoParent2))
        dto.children = ArrayList(listOf(dtoChild1, dtoChild2, dtoChild3))

        val statusEntity = template.postForEntity("$base/add-person", dto, HttpStatus::class.java)
        assertThat(statusEntity.statusCode).isEqualTo(HttpStatus.CREATED)

        dto.parent = ArrayList(listOf(dtoParent3))
        val updateHttp = HttpEntity(dto)
        val httpStatusUpdateEntity = template.exchange(
            "$base/update-person",
                HttpMethod.PUT, updateHttp, HttpStatus::class.java)
        assertThat(httpStatusUpdateEntity.getStatusCode()).isEqualTo(HttpStatus.OK)

        val deleteHttp = HttpEntity(dto.passport)
        val httpStatusDeleteEntity = template.exchange(
            "$base/delete-person",
                HttpMethod.DELETE, deleteHttp, HttpStatus::class.java)
        assertThat(httpStatusDeleteEntity.statusCode).isEqualTo(HttpStatus.OK)
    }
}