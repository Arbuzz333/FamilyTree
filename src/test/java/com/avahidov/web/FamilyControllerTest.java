package com.avahidov.web;

import com.avahidov.web.dto.FamilyPersonDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FamilyControllerTest {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @BeforeEach
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/avahidov/family-tree");
    }

    @Test
    public void getPersonTree() {
        ResponseEntity<FamilyPersonDto> responseEntity = template.getForEntity(base.toString() + "/10001000", FamilyPersonDto.class);
        FamilyPersonDto body = responseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        String actual = "";
        try {
            actual = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertThat(actual).isEqualTo("{\"passport\":10001000,\"name\":\"Ivanov\",\"secondName\":\"Ivanovich\",\"parent\":[{\"passport\":10001001,\"name\":\"Lev\",\"secondName\":\"Dymich\",\"parent\":[{\"passport\":10001011,\"name\":\"Alrksandra\",\"secondName\":\"Timurovna\",\"parent\":[],\"sisterBrother\":[],\"children\":[]}],\"sisterBrother\":[],\"children\":[]},{\"passport\":20001001,\"name\":\"Lena\",\"secondName\":\"Aleksandrovna\",\"parent\":[],\"sisterBrother\":[],\"children\":[]}],\"sisterBrother\":[{\"passport\":20001000,\"name\":\"Denis\",\"secondName\":\"Ivanovich\",\"parent\":[],\"sisterBrother\":[],\"children\":[]}],\"children\":[{\"passport\":10011000,\"name\":\"Aleksey\",\"secondName\":\"Ivanovich\",\"parent\":[],\"sisterBrother\":[],\"children\":[{\"passport\":10111000,\"name\":\"Anton\",\"secondName\":\"Alekseych\",\"parent\":[],\"sisterBrother\":[],\"children\":[]}]},{\"passport\":10021000,\"name\":\"Andrey\",\"secondName\":\"Ivanovich\",\"parent\":[],\"sisterBrother\":[],\"children\":[]}]}");
    }
}