package com.neon.releasetracker.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neon.releasetracker.models.Release;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ReleaseControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("/testData.sql")
    public void getReleaseById() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/releases/{id}", 1)).andExpect(status().isOk()).andReturn();
        Release releaseFromJson = objectMapper.readValue(result.getResponse().getContentAsString(), Release.class);

        assertEquals(1, releaseFromJson.getId());
    }

    @Test
    @Sql("/testData.sql")
    public void getReleases() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/v1/releases")).andExpect(status().isOk()).andReturn();
        List<Release> releaseFromJson = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Release>>() {
                });

        assertEquals(1, releaseFromJson.size());
        assertEquals(1, releaseFromJson.get(0).getId());
    }

    @Test
    public void getReleasesError() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/releases/{id}", 2)).andExpect(status().is4xxClientError()).andReturn();
        String error = result.getResponse().getContentAsString();

        assertEquals(error, "Release doesn't exists");
    }

    @Test
    public void newRelease() {
        Release release = new Release(2, "Name 2", "Description 2", "In Development", new Date(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));

        ResponseEntity<Release> createdRelease = testRestTemplate.postForEntity("/api/v1/releases", release, Release.class);

        assertEquals(createdRelease.getBody().getId(), 2);
    }

    @Test
    public void newReleaseError() {
        Release release = new Release(2, "Name 2", "Description 2", "In DevelopmentError", new Date(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));

        ResponseEntity<String> message = testRestTemplate.postForEntity("/api/v1/releases", release, String.class);

        assertThat(message.getStatusCode().is4xxClientError());
        assertEquals(message.getBody(), "Status not valid.");
    }

    @Test
    public void updateRelease() throws Exception {
        Release release = new Release(2, "Name 2", "Description 2", "In Development", new Date(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));

        MvcResult result = mockMvc.perform(put("/api/v1/releases/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(release))
                        .header("Accept", "*/*"))
                .andExpect(status().isOk()).andReturn();

        Release releaseFromJson = objectMapper.readValue(result.getResponse().getContentAsString(), Release.class);

        assertEquals(releaseFromJson.getId(), 1);
        assertEquals(releaseFromJson.getName(), "Name 2");
    }

    @Test
    public void updateReleaseError() throws Exception {
        Release release = new Release(2, "Name 2", "Description 2", "In DevelopmentError",
                new Date(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()));

        MvcResult result = mockMvc.perform(put("/api/v1/releases/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(release))
                        .header("Accept", "*/*"))
                .andExpect(status().is4xxClientError()).andReturn();

        String message = result.getResponse().getContentAsString();

        assertEquals(message, "Status not valid.");
    }

    @Test
    public void deleteRelease() throws Exception {

        MvcResult result = mockMvc.perform(delete("/api/v1/releases/{id}", 1))
                .andExpect(status().isOk()).andReturn();

        assertEquals(result.getResponse().getContentAsString(), "successful");
    }

    @Test
    public void deleteReleaseError() throws Exception {

        MvcResult result = mockMvc.perform(delete("/api/v1/releases/{id}", 5))
                .andExpect(status().is4xxClientError()).andReturn();

        assertEquals(result.getResponse().getContentAsString(), "Release doesn't exists");
    }
}
