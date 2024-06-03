package com.expeditors.musictracking.controller;

import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.Role;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getAllTracks() throws Exception {
        MockHttpServletRequestBuilder builder = get("/Tracks")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }

    @Test
    public void getById() throws Exception {
        MockHttpServletRequestBuilder builder = get("/Tracks/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());

        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        Track track = mapper.treeToValue(node.get("entity"), Track.class);

        assertNotNull(track);

        builder = get("/Tracks/{id}",2903)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void getByTitle() throws Exception {
        MockHttpServletRequestBuilder builder = get("/Tracks/getByTitle/{title}", "Love")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());

        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        List<Track> tracks = mapper.treeToValue(node.get("entity"), List.class);

        assertFalse(tracks.isEmpty());

        builder = get("/Tracks/getByTitle/{title}", "Magic")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void getByAlbum() throws Exception {
        MockHttpServletRequestBuilder builder = get("/Tracks/getByAlbum/{album}", "My Love is the end")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());

        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        List<Track> tracks = mapper.treeToValue(node.get("entity"), List.class);

        assertFalse(tracks.isEmpty());

        builder = get("/Tracks/getByAlbum/{album}", "Magic Magic")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void getByArtist() throws Exception {
        MockHttpServletRequestBuilder builder = get("/Tracks/getByArtist/{artist}", "Michel Jackson")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());

        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        List<Track> tracks = mapper.treeToValue(node.get("entity"), List.class);

        assertFalse(tracks.isEmpty());

        builder = get("/Tracks/getByArtist/{artist}", "Mi mi mi")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isNotFound());

    }

    @Test
    public void getByMediaType() throws Exception {
        MockHttpServletRequestBuilder builder = get("/Tracks/getByMediaType/{mediaType}", "MP3")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());

        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        List<Track> tracks = mapper.treeToValue(node.get("entity"), List.class);

        assertFalse(tracks.isEmpty());

        builder = get("/Tracks/getByArtist/{artist}", "AVI")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isNotFound());

        builder = get("/Tracks/getByArtist/{artist}", "Ogg")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isNotFound());

    }

    @Test
    public void getByDuration() throws Exception {
        MockHttpServletRequestBuilder builder = get("/Tracks/getByDuration/{duration}/{filter}",1,"LessThan")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());

        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        List<Track> tracks = mapper.treeToValue(node.get("entity"), List.class);

        assertFalse(tracks.isEmpty());

        builder = get("/Tracks/getByDuration/{duration}/{filter}",233,"Equals")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isNotFound());

    }

    @Test
    public void getByYear() throws Exception {
        MockHttpServletRequestBuilder builder = get("/Tracks/getByYear/{year}",1981)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());

        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        List<Track> tracks = mapper.treeToValue(node.get("entity"), List.class);

        assertFalse(tracks.isEmpty());

        builder = get("/Tracks/getByYear/{year}",2333)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isNotFound());

    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void insertTracks() throws Exception {
        Track tracks = new Track(
                213,
                "Disco Majul",
                "Colorama",
                new Date("2029/10/02"),
                new Artist(
                        20,
                        "Gativideo",
                        0,
                        new Date("2010/02/23"),
                        "Spain",
                        Genre.Pop,
                        Role.Producer),
                10.40);

        String jsonString = mapper.writeValueAsString(tracks);

        ResultActions actions = mockMvc.perform(post("/Tracks")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));

        actions = actions.andExpect(status().isCreated());

        MvcResult result = actions.andReturn();
        String locHeader = result.getResponse().getHeader("Location");
        assertNotNull(locHeader);
        System.out.println("locHeader: " + locHeader);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteTracks() throws Exception {
        ResultActions actions = mockMvc.perform(delete("/Tracks/{id}", 1000)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isNotFound());

        Track track = new Track(
                213,
                "Disco Majul",
                "Colorama",
                new Date("2029/10/02"),
                new Artist(
                        20,
                        "Gativideo",
                        0,
                        new Date("2010/02/23"),
                        "Spain",
                        Genre.Pop,
                        Role.Producer),
                10.40);

        String jsonString = mapper.writeValueAsString(track);

        actions = mockMvc.perform(post("/Tracks")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));

        actions.andExpect(status().isCreated());

        actions = mockMvc.perform(delete("/Tracks/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void updateTracks() throws Exception {
        MockHttpServletRequestBuilder builder = get("/Tracks/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());
        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        Track track = mapper.treeToValue(node.get("entity"), Track.class);
        track.setTitle("Wondeful world");

        String updateEntity = mapper.writeValueAsString(track);

        actions = mockMvc.perform(put("/Tracks")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateEntity));

        actions.andExpect(status().isOk());

        builder = get("/Tracks/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        actions = mockMvc.perform(builder)
                .andExpect(status().isOk());
        result = actions.andReturn();

        jsonResult = result.getResponse().getContentAsString();
        node = mapper.readTree(jsonResult);
        track = mapper.treeToValue(node.get("entity"), Track.class);

        assertEquals("Wondeful world", track.getTitle());

        track.setTrackId(100);
        updateEntity = mapper.writeValueAsString(track);
        actions = mockMvc.perform(put("/Tracks")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateEntity));

        actions.andExpect(status().isNotFound());
    }

}
