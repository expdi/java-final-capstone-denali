package com.expeditors.musictracking.controller;

import com.expeditors.musictracking.model.Artist;
import com.expeditors.musictracking.model.Track;
import com.expeditors.musictracking.model.enumerator.Genre;
import com.expeditors.musictracking.model.enumerator.Role;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
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
public class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getAllArtists() throws Exception {
        MockHttpServletRequestBuilder builder = get("/Artist")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }

    @Test
    public void getById() throws Exception {

        ResultActions actions = mockMvc.perform( get("/Artist")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        List<Artist> artists = mapper.readValue(node.get("entity").toString(), new TypeReference<List<Artist>>() {});
        Artist lastArtist = artists.getLast();

        MockHttpServletRequestBuilder builder = get("/Artist/{id}", lastArtist.getArtistId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        actions = mockMvc.perform(builder)
                .andExpect(status().isOk());

        result = actions.andReturn();

        jsonResult = result.getResponse().getContentAsString();
        node = mapper.readTree(jsonResult);
        Artist artist = mapper.treeToValue(node.get("entity"), Artist.class);

        assertNotNull(artist);

        builder = get("/Artist/{id}",2903)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void getByName() throws Exception {
        MockHttpServletRequestBuilder builder = get("/Artist/getByName/{name}", "Michael Jackson")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());

        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        List<Artist> artists = mapper.treeToValue(node.get("entity"), List.class);

        assertFalse(artists.isEmpty());

        builder = get("/Artist/getByName/{name}","Bruno Mars")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void getByRole() throws Exception {
        MockHttpServletRequestBuilder builder = get("/Artist/getByRole/{role}", "Singer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());

        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        List<Artist> artists = mapper.treeToValue(node.get("entity"), List.class);

        assertFalse(artists.isEmpty());

        builder = get("/Artist/getByRole/{role}","Doctor")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isBadRequest());

        builder = get("/Artist/getByRole/{role}","Producer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void getByMusicGenre() throws Exception {
        MockHttpServletRequestBuilder builder = get("/Artist/getByMusicGenre/{genre}", "Pop")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());

        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        List<Artist> artists = mapper.treeToValue(node.get("entity"), List.class);

        assertFalse(artists.isEmpty());

        builder = get("/Artist/getByMusicGenre/{genre}","Hiphop")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void getTracksByArtist() throws Exception {
        MockHttpServletRequestBuilder builder = get("/Artist/getTracks/{artist}", "Michael Jackson")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());

        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        List<Track> artists = mapper.treeToValue(node.get("entity"), List.class);

        assertFalse(artists.isEmpty());

        builder = get("/Artist/getTracks/{artist}","Weber")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder)
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Transactional
    public void insertArtist() throws Exception {
        Artist artist = new Artist(
                "Michel Jackson",
                1.92,
                new Date("1968/08/15"),
                "California",
                Genre.Pop,
                Role.Singer);

        String jsonString = mapper.writeValueAsString(artist);

        ResultActions actions = mockMvc.perform(post("/Artist")
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
    @Transactional
    public void deleteArtist() throws Exception {
        ResultActions actions = mockMvc.perform(delete("/Artist/{id}", 1000)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isNotFound());

        Artist artist = new Artist(
                "Michel Jackson",
                1.92,
                new Date("1968/08/15"),
                "California",
                Genre.Pop,
                Role.Singer);

        String jsonString = mapper.writeValueAsString(artist);

        actions = mockMvc.perform(post("/Artist")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));

        actions.andExpect(status().isCreated());

        actions = mockMvc.perform(delete("/Artist/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Transactional
    public void updateArtist() throws Exception {
        MockHttpServletRequestBuilder builder = get("/Artist/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());
        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        Artist artist = mapper.treeToValue(node.get("entity"), Artist.class);
        artist.setName("Billy Joel");

        String updateEntity = mapper.writeValueAsString(artist);

        actions = mockMvc.perform(put("/Artist")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateEntity));

        actions.andExpect(status().isOk());

        builder = get("/Artist/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        actions = mockMvc.perform(builder)
                .andExpect(status().isOk());
        result = actions.andReturn();

        jsonResult = result.getResponse().getContentAsString();
        node = mapper.readTree(jsonResult);
        artist = mapper.treeToValue(node.get("entity"), Artist.class);

        assertEquals("Billy Joel", artist.getName());

        artist.setArtistId(100);
        updateEntity = mapper.writeValueAsString(artist);
        actions = mockMvc.perform(put("/Artist")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateEntity));

        actions.andExpect(status().isNotFound());

    }

}
