package com.neon.releasetracker.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neon.releasetracker.exceptions.CustomException;
import com.neon.releasetracker.models.Release;
import com.neon.releasetracker.services.ReleaseService;
import com.neon.releasetracker.validators.StatusValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReleaseController.class)
@AutoConfigureMockMvc
@Slf4j
class ReleaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReleaseService releaseService;

    @MockBean
    private StatusValidator validator;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllReleases() throws Exception {

        List<Release> releaseList = new ArrayList<>();
        Release release = new Release(1, "name", "description", "Done",
                new Date(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()));

        releaseList.add(release);

        Mockito.when(releaseService.getAllReleases()).thenReturn(releaseList);

        MvcResult result = mockMvc.perform(get("/api/v1/releases")).andExpect(status().isOk()).andReturn();
        List<Release> releaseFromJson = objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Release>>() {
                });

        for (int i = 0; i < releaseFromJson.size(); i++) {
            assertEquals(releaseFromJson.get(i).getName(), releaseList.get(i).getName());
            assertEquals(releaseFromJson.get(i).getDescription(), releaseList.get(i).getDescription());
            assertEquals(releaseFromJson.get(i).getStatus(), releaseList.get(i).getStatus());
            Calendar calendarResponse = Calendar.getInstance();
            calendarResponse.setTime(releaseFromJson.get(i).getReleaseDate());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(releaseList.get(i).getReleaseDate());
            assertEquals(calendar.get(Calendar.YEAR), calendarResponse.get(Calendar.YEAR));
            assertEquals(calendar.get(Calendar.MONTH), calendarResponse.get(Calendar.MONTH));
            assertEquals(calendar.get(Calendar.DATE), calendarResponse.get(Calendar.DATE));
            assertEquals(releaseFromJson.get(i).getCreatedAt(), releaseList.get(i).getCreatedAt());
            assertEquals(releaseFromJson.get(i).getLastUpdatedAt(), releaseList.get(i).getLastUpdatedAt());
        }
    }

    @Test
    void getRelease() throws Exception {
        Release release = new Release(1, "name", "description", "Done",
                new Date(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()));

        Mockito.when(releaseService.getRelease(1)).thenReturn(release);

        MvcResult result = mockMvc.perform(get("/api/v1/releases/{id}", 1)).andExpect(status().isOk()).andReturn();
        Release releaseFromJson = objectMapper.readValue(result.getResponse().getContentAsString(), Release.class);

        assertEquals(releaseFromJson.getName(), release.getName());
        assertEquals(releaseFromJson.getDescription(), release.getDescription());
        assertEquals(releaseFromJson.getStatus(), release.getStatus());
        Calendar calendarResponse = Calendar.getInstance();
        calendarResponse.setTime(releaseFromJson.getReleaseDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(release.getReleaseDate());
        assertEquals(calendar.get(Calendar.YEAR), calendarResponse.get(Calendar.YEAR));
        assertEquals(calendar.get(Calendar.MONTH), calendarResponse.get(Calendar.MONTH));
        assertEquals(calendar.get(Calendar.DATE), calendarResponse.get(Calendar.DATE));
        assertEquals(releaseFromJson.getCreatedAt(), release.getCreatedAt());
        assertEquals(releaseFromJson.getLastUpdatedAt(), release.getLastUpdatedAt());
    }

    @Test
    void getReleaseRequestParam() throws Exception {
        Release release = new Release(1, "name", "description", "Done",
                new Date(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()));

        Mockito.when(releaseService.getRelease(1)).thenReturn(release);

        MvcResult result = mockMvc.perform(get("/api/v1/release").param("id", String.valueOf(1)))
                .andExpect(status().isOk()).andReturn();
        Release releaseFromJson = objectMapper.readValue(result.getResponse().getContentAsString(), Release.class);

        assertEquals(releaseFromJson.getName(), release.getName());
        assertEquals(releaseFromJson.getDescription(), release.getDescription());
        assertEquals(releaseFromJson.getStatus(), release.getStatus());
        Calendar calendarResponse = Calendar.getInstance();
        calendarResponse.setTime(releaseFromJson.getReleaseDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(release.getReleaseDate());
        assertEquals(calendar.get(Calendar.YEAR), calendarResponse.get(Calendar.YEAR));
        assertEquals(calendar.get(Calendar.MONTH), calendarResponse.get(Calendar.MONTH));
        assertEquals(calendar.get(Calendar.DATE), calendarResponse.get(Calendar.DATE));
        assertEquals(releaseFromJson.getCreatedAt(), release.getCreatedAt());
        assertEquals(releaseFromJson.getLastUpdatedAt(), release.getLastUpdatedAt());
    }

    @Test
    void newRelease() throws Exception {
        Release release = new Release(1, "name", "description", "Done",
                new Date(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()));

        Mockito.when(releaseService.newRelease(any(Release.class))).thenReturn(release);


        MvcResult result = mockMvc.perform(post("/api/v1/releases").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(release)))
                .andExpect(status().isOk()).andReturn();

        Release releaseFromJson = objectMapper.readValue(result.getResponse().getContentAsString(), Release.class);

        assertEquals(releaseFromJson.getName(), release.getName());
        assertEquals(releaseFromJson.getDescription(), release.getDescription());
        assertEquals(releaseFromJson.getStatus(), release.getStatus());
        Calendar calendarResponse = Calendar.getInstance();
        calendarResponse.setTime(releaseFromJson.getReleaseDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(release.getReleaseDate());
        assertEquals(calendar.get(Calendar.YEAR), calendarResponse.get(Calendar.YEAR));
        assertEquals(calendar.get(Calendar.MONTH), calendarResponse.get(Calendar.MONTH));
        assertEquals(calendar.get(Calendar.DATE), calendarResponse.get(Calendar.DATE));
        assertEquals(releaseFromJson.getCreatedAt(), release.getCreatedAt());
        assertEquals(releaseFromJson.getLastUpdatedAt(), release.getLastUpdatedAt());
    }

    @Test
    void newReleaseError() throws Exception {
        Release release = new Release(1, "name", "description", "Wrong status",
                new Date(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()));

        Mockito.when(releaseService.newRelease(any(Release.class))).thenReturn(release);

        Mockito.when(validator.checkStatus(release.getStatus()))
                .thenThrow(new CustomException(HttpStatus.BAD_REQUEST, "Status not valid."));

        MvcResult result = mockMvc.perform(post("/api/v1/releases").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(release)))
                .andExpect(status().is4xxClientError()).andReturn();
    }

    @Test
    void updateRelease() throws Exception {
        Release release = new Release(1, "name", "description", "Done",
                new Date(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()));

        Mockito.when(releaseService.updateRelease(anyInt(), any(Release.class))).thenReturn(release);

        MvcResult result = mockMvc.perform(put("/api/v1/releases/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(release))
                        .header("Accept", "*/*"))
                .andExpect(status().isOk()).andReturn();

        Release releaseFromJson = objectMapper.readValue(result.getResponse().getContentAsString(), Release.class);

        assertEquals(releaseFromJson.getName(), release.getName());
        assertEquals(releaseFromJson.getDescription(), release.getDescription());
        assertEquals(releaseFromJson.getStatus(), release.getStatus());
        Calendar calendarResponse = Calendar.getInstance();
        calendarResponse.setTime(releaseFromJson.getReleaseDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(release.getReleaseDate());
        assertEquals(calendar.get(Calendar.YEAR), calendarResponse.get(Calendar.YEAR));
        assertEquals(calendar.get(Calendar.MONTH), calendarResponse.get(Calendar.MONTH));
        assertEquals(calendar.get(Calendar.DATE), calendarResponse.get(Calendar.DATE));
        assertEquals(releaseFromJson.getCreatedAt(), release.getCreatedAt());
        assertEquals(releaseFromJson.getLastUpdatedAt(), release.getLastUpdatedAt());
    }

    @Test
    void deleteRelease() throws Exception {
        Mockito.when(releaseService.deleteRelease(anyInt())).thenReturn("Successful");

        mockMvc.perform(delete("/api/v1/releases/{id}", 1)).andExpect(status().isOk());
    }
}