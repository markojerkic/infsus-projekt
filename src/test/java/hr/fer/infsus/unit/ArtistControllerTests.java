package hr.fer.infsus.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import hr.fer.infsus.model.Artist;
import hr.fer.infsus.repository.ArtistRepository;
import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashSet;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ArtistControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArtistRepository artistRepository;

    @BeforeEach
    public void setUp() {
        artistRepository.deleteAll();
    }

    @Test
    public void testGetNewArtist() throws Exception {
        mockMvc.perform(get("/artist/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("artist/new"))
                .andExpect(model().attributeExists("artist"));
    }

    @Test
    public void testCheckHtmlFormContent() throws Exception {
        var artist = this.createArtist();
        mockMvc.perform(get("/artist/" + artist.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        org.hamcrest.Matchers.containsString(artist.getName())))
                .andExpect(content().string(
                        org.hamcrest.Matchers.containsString(artist.getLastname())))
                .andExpect(content().string(
                        org.hamcrest.Matchers.containsString(artist.getUsername())));
    }

    @Test
    public void testEditNonExistentArtist() throws Exception {
        mockMvc.perform(get("/artist/edit/2"))
                .andExpect(status().isNotFound());
    }

    private Artist createArtist() {
        var artist = new Artist();
        artist.setId(1L);
        artist.setName("Marko");
        artist.setLastname("Jerkić");
        artist.setUsername("username");
        artist.setType("type");
        artist.setArtworks(new ArrayList<>());
        artist.setSavedArtworks(new HashSet<>());
        var saved = artistRepository.save(artist);
        saved.getArtworks().size();
        return saved;
    }

}
