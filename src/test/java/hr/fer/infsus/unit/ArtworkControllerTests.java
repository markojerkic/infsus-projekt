package hr.fer.infsus.unit;

import org.mockito.junit.MockitoJUnitRunner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import hr.fer.infsus.model.Artist;
import hr.fer.infsus.model.Artwork;
import hr.fer.infsus.repository.ArtistRepository;
import hr.fer.infsus.repository.ArtworkRepository;
import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.HashSet;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ArtworkControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ArtworkRepository artworkRepository;
    @Autowired
    private ArtistRepository artistRepository;

    @BeforeEach
    public void setUp() {
        artworkRepository.deleteAll();
    }

    @Test
    public void testGetNewArtwork() throws Exception {
        this.createArtist();
        mockMvc.perform(get("/artwork/1/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("artwork/new"))
                .andExpect(model().attributeExists("artwork"));
    }

    private Artwork createArtwork(Artist artist) {
        var artwork = new Artwork();
        artwork.setName("Artwork markovo djelo neko");
        artwork.setDescription("Nekimoj opis");
        artwork.setArtist(artist);
        artwork.setCollection(null);
        return artworkRepository.save(artwork);
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
