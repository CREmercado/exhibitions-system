package com.epam.exhibitions.controller;

import com.epam.exhibitions.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.context.SpringBootTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserHttpRequestTest {

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
    private final static String EXHIBITION_PATH = "/api/users";

    @BeforeEach
    void setUp() throws JsonProcessingException {
        User user = this.userFactory();
        this.mapper = new ObjectMapper();
        this.jsonStr = this.mapper.writeValueAsString(user);
    }

    @Test
    public void getUsers_shouldReturnSuccessfulState() throws Exception {
        String userPath = MAIN_PATH + this.port + EXHIBITION_PATH;
        HttpHeaders headers = this.setHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);
        this.mockMvc.perform(get(userPath)
                .headers(headers))
                .andExpect(status().isOk());
        assertThat(this.restTemplate.exchange(userPath, HttpMethod.GET, request, String.class)
                .getStatusCode().is2xxSuccessful());
    }

    @Test
    public void getUserById_shouldReturnSuccessfulState() throws Exception {
        Long userId = 1L;
        String userPath = this.MAIN_PATH + this.port + this.EXHIBITION_PATH + "/" + userId;
        HttpHeaders headers = this.setHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);
        this.mockMvc.perform(get(userPath)
                .headers(headers))
                .andExpect(status().isOk());
        assertThat(this.restTemplate.exchange(userPath, HttpMethod.GET, request, String.class)
                .getStatusCode().is2xxSuccessful());
    }

    @Test
    public void addUser_shouldReturnSuccessfulState_shouldThrowException() throws Exception {
        String userPath = this.MAIN_PATH + this.port + this.EXHIBITION_PATH;
        HttpHeaders headers = this.setHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);
        this.mockMvc.perform(post(userPath)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.jsonStr)
                .headers(headers))
                .andExpect(status().isOk());
        assertThat(this.restTemplate.exchange(userPath, HttpMethod.POST, request, String.class)
                .getStatusCode().is2xxSuccessful());

        // test for checking the throwing of exception
        this.jsonStr = mapper.writeValueAsString(null);
        assertThat(mockMvc.perform(post(userPath)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.jsonStr)
                .headers(headers))).getClass().equals(Exception.class);
    }

    @Test
    public void updateUserById_shouldReturnSuccessfulState_shouldThrowException() throws Exception {
        User newUser = new User(1L, "Mark", "McCarthy", "Mcar", "12345", "User");
        this.jsonStr = this.mapper.writeValueAsString(newUser);
        String userPath = this.MAIN_PATH + this.port + this.EXHIBITION_PATH + "/" + newUser.getUserId();
        HttpHeaders headers = this.setHeaders();
        HttpEntity<String> request = new HttpEntity<String>(headers);
        this.mockMvc.perform((RequestBuilder) put(userPath)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.jsonStr)
                .headers(headers))
                .andExpect(status().isOk())
                .toString().contains("User updated successfully!");
        assertThat(this.restTemplate.exchange(userPath, HttpMethod.PUT, request, String.class)
                .getStatusCode().is2xxSuccessful());

        // test for checking the throwing of exception
        this.jsonStr = mapper.writeValueAsString(null);
        assertThat(mockMvc.perform(post(userPath)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(this.jsonStr)
                .headers(headers))).getClass().equals(Exception.class);
    }

    private User userFactory() {
        return User.builder()
                .firstName("Henry")
                .lastName("McCarthy")
                .nickname("Hcar")
                .password("12345")
                .role("User")
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
