package com.epam.exhibitions.controller;

import com.epam.exhibitions.entity.*;
import com.epam.exhibitions.entity.model.Dates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ExhibitionHttpRequestTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    private String jsonStr;
    private final static String MAIN_PATH = "http://localhost:";
    private final static String EXHIBITION_PATH = "/api/exhibitions";

    @BeforeEach
    void setUp() throws JsonProcessingException {
        Hall hall = this.hallFactory();
        Exhibition exhibition = this.exhibitionFactory(hall);
        this.mapper.registerModule(new JavaTimeModule());
        this.jsonStr = this.mapper.writeValueAsString(exhibition);
    }

    @Test
    void getExhibitions_shouldReturnSuccessfulState() throws Exception {
        String exhibitionPath = this.MAIN_PATH + this.port + this.EXHIBITION_PATH;
        HttpHeaders headers = this.setHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);
        this.mockMvc.perform(get(exhibitionPath)
                .headers(headers))
                .andExpect(status().isOk());
        assertThat(this.restTemplate.exchange(exhibitionPath, HttpMethod.GET, request, String.class)
                .getStatusCode().is2xxSuccessful());
    }

    @Test
    void getExhibitionById_shouldReturnSuccessfulState() throws Exception {
        Long exhibitionId = 2L;
        String exhibitionPath = this.MAIN_PATH + this.port + this.EXHIBITION_PATH + "/" + exhibitionId;
        HttpHeaders headers = this.setHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);
        this.mockMvc.perform(get(exhibitionPath)
                .headers(headers))
                .andExpect(status().isOk());
        assertThat(this.restTemplate.exchange(exhibitionPath, HttpMethod.GET, request, String.class)
                .getStatusCode().is2xxSuccessful());
    }

    @Test
    void getActiveExhibitions_shouldReturnSuccessfulState() throws Exception {
        String exhibitionState = "active";
        String exhibitionPath = this.MAIN_PATH + this.port + this.EXHIBITION_PATH + "/" + exhibitionState;
        HttpHeaders headers = this.setHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);
        this.mockMvc.perform(get(exhibitionPath)
                .headers(headers))
                .andExpect(status().isOk());
        assertThat(this.restTemplate.exchange(exhibitionPath, HttpMethod.GET, request, String.class)
                .getStatusCode().is2xxSuccessful());
    }

    @Test
    void getActiveExhibitionsFilterByDate_shouldReturnSuccessfulState() throws Exception {
        String exhibitionStateByDate = "/" + "active" + "/" + "byDate";
        String exhibitionPath = this.MAIN_PATH + this.port + this.EXHIBITION_PATH + "/" + exhibitionStateByDate;
        LocalDate startDate = LocalDate.now().minusMonths(10);
        LocalDate endDate = LocalDate.now().plusMonths(10);
        Dates dates = new Dates(startDate, endDate);
        this.jsonStr = this.mapper.writeValueAsString(dates);
        HttpHeaders headers = this.setHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);
        this.mockMvc.perform(get(exhibitionPath)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.jsonStr)
                .headers(headers))
                .andExpect(status().isOk());
        assertThat(this.restTemplate.exchange(exhibitionPath, HttpMethod.GET, request, String.class)
                .getStatusCode().is2xxSuccessful());
    }

    @Test
    void getExhibitionHallsById_shouldReturnSuccessfulState() throws Exception {
        Long exhibitionId = 1L;
        String exhibitionHallById = "/" + "halls" + "/" + exhibitionId;
        String exhibitionPath = MAIN_PATH + this.port + EXHIBITION_PATH + "/" + exhibitionHallById;
        HttpHeaders headers = this.setHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);
        this.mockMvc.perform(get(exhibitionPath)
                .headers(headers))
                .andExpect(status().isOk());
        assertThat(this.restTemplate.exchange(exhibitionPath, HttpMethod.GET, request, String.class)
                .getStatusCode().is2xxSuccessful());
    }

    @Test
    void addExhibition_shouldReturnSuccessfulState_shouldThrowException() throws Exception {
        String exhibitionPath = MAIN_PATH + this.port + EXHIBITION_PATH;
        HttpHeaders headers = this.setHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);
        this.mockMvc.perform(post(exhibitionPath)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.jsonStr)
                .headers(headers))
                .andExpect(status().isOk());
        assertThat(this.restTemplate.exchange(exhibitionPath, HttpMethod.POST, request, String.class)
                .getStatusCode().is2xxSuccessful());

        // test for checking the throwing of exception
        this.jsonStr = mapper.writeValueAsString(null);
        assertThat(mockMvc.perform(post(exhibitionPath)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.jsonStr)
                .headers(headers))).getClass().equals(Exception.class);
    }

    private Hall hallFactory() {
        return Hall.builder()
                .hallName("Art Hall")
                .hallCity("USA")
                .hallCountry("Boston")
                .build();
    }

    private Exhibition exhibitionFactory(Hall hall) {
        return Exhibition.builder()
                .theme("Art")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(5L))
                .openingTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .closingTime(LocalDateTime.now().plusHours(8L).truncatedTo(ChronoUnit.SECONDS))
                .ticketPrice(400.5)
                .state(Boolean.TRUE)
                .halls(new HashSet<>(Collections.singletonList(hall)))
                .build();
    }

    private HttpHeaders setHeaders() {
        String credentials = "user" + ":" + "userPass";
        byte[] credentialBytes = credentials .getBytes();
        byte[] base64CredentialBytes = Base64.encodeBase64(credentialBytes);
        String base64Credentials = new String(base64CredentialBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        return headers;
    }
}