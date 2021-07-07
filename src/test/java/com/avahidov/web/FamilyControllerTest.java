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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

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
        assertThat(actual).isEqualTo("{\"passport\":10001000,\"name\":\"Ivanov\",\"secondName\":\"Ivanovich\",\"parent\":[{\"passport\":10001001,\"name\":\"Lev\",\"secondName\":\"Dymich\",\"parent\":[{\"passport\":10001011,\"name\":\"Alrksandra\",\"secondName\":\"Timurovna\"}]},{\"passport\":20001001,\"name\":\"Lena\",\"secondName\":\"Aleksandrovna\"}],\"sisterBrother\":[{\"passport\":20001000,\"name\":\"Denis\",\"secondName\":\"Ivanovich\"}],\"children\":[{\"passport\":10011000,\"name\":\"Aleksey\",\"secondName\":\"Ivanovich\",\"children\":[{\"passport\":10111000,\"name\":\"Anton\",\"secondName\":\"Alekseych\"}]},{\"passport\":10021000,\"name\":\"Andrey\",\"secondName\":\"Ivanovich\"}]}");
    }

    @Test
    public void addUpdateDeletePersonTree() {
        FamilyPersonDto dtoParent_1 = new FamilyPersonDto(50004000L, "", "", new ArrayList<>(), new TreeSet<>(), new ArrayList<>());
        FamilyPersonDto dtoParent_2 = new FamilyPersonDto(50004001L, "", "", new ArrayList<>(), new TreeSet<>(), new ArrayList<>());
        FamilyPersonDto dtoParent_3 = new FamilyPersonDto(50004002L, "", "", new ArrayList<>(), new TreeSet<>(), new ArrayList<>());

        FamilyPersonDto dtoChild_1 = new FamilyPersonDto(50005001L, "", "", new ArrayList<>(), new TreeSet<>(), new ArrayList<>());
        FamilyPersonDto dtoChild_2 = new FamilyPersonDto(50005002L, "", "", new ArrayList<>(), new TreeSet<>(), new ArrayList<>());
        FamilyPersonDto dtoChild_3 = new FamilyPersonDto(50005003L, "", "", new ArrayList<>(), new TreeSet<>(), new ArrayList<>());
        FamilyPersonDto dto = new FamilyPersonDto(50005000L, "Tom", "Tomson", Arrays.asList(dtoParent_1, dtoParent_2),
                new TreeSet<>(), Arrays.asList(dtoChild_1, dtoChild_2, dtoChild_3));

        ResponseEntity<HttpStatus> statusEntity = template.postForEntity(base.toString() + "/add-person", dto, HttpStatus.class);

        assertThat(statusEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        dto.setParent(Collections.singletonList(dtoParent_3));
        HttpEntity<FamilyPersonDto> updateHttp = new HttpEntity(dto);
        ResponseEntity<HttpStatus> httpStatusUpdateEntity = template.exchange(base.toString() + "/update-person",
                HttpMethod.PUT, updateHttp, HttpStatus.class);

        assertThat(httpStatusUpdateEntity.getStatusCode()).isEqualTo(HttpStatus.OK);


        HttpEntity<FamilyPersonDto> deleteHttp = new HttpEntity(dto.getPassport());
        ResponseEntity<HttpStatus> httpStatusDeleteEntity = template.exchange(base.toString() + "/delete-person",
                HttpMethod.DELETE, deleteHttp, HttpStatus.class);
        assertThat(httpStatusDeleteEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}