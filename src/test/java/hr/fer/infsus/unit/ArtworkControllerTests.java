package hr.fer.infsus.unit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import hr.fer.infsus.model.Artist;
import hr.fer.infsus.model.Artwork;
import hr.fer.infsus.model.Collection;
import hr.fer.infsus.model.types.sif.Genre;
import hr.fer.infsus.repository.ArtistRepository;
import hr.fer.infsus.repository.ArtworkRepository;
import hr.fer.infsus.repository.CollectionRepository;
import hr.fer.infsus.repository.GenreRepository;
import jakarta.transaction.Transactional;

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
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    public void setUp() {
        this.artworkRepository.deleteAll();
        this.artistRepository.deleteAll();
        this.collectionRepository.deleteAll();
        this.genreRepository.deleteAll();
    }

    @Test
    public void testGetNewArtwork() throws Exception {
        this.createArtist();
        mockMvc.perform(get("/artwork/1/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("artwork/new"))
                .andExpect(model().attributeExists("artwork"));
    }

    @Test
    public void testCreateNewArtworkForNonExistentArtist() throws Exception {
        mockMvc.perform(
                multipart("/artwork/1/new")
                        .file("video.file", new byte[] {})
                        .param("name", "Artwork")
                        .param("description", "Description")
                        .param("collectionId", "1")
                        .param("artistId", "1")
                        .param("video.duration", "2s")
                        .param("video.genreId", "1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testUploadFileWrongFormat() throws Exception {
        var artist = this.createArtist();
        var collection = this.createCollection();
        var genre = this.createGenre();

        var mmpf = new MockMultipartFile("video.file", "test.txt", "text/plain", new byte[] {});

        mockMvc.perform(
                multipart("/artwork/" + artist.getId() + "/new")
                        .file(mmpf)
                        .param("name", "Artwork")
                        .param("description", "Description")
                        .param("collectionId", collection.getId().toString())
                        .param("artistId", artist.getId().toString())
                        .param("video.duration", "2s")
                        .param("video.genreId", genre.getId().toString()))
                .andExpect(content().string(
                        org.hamcrest.Matchers.containsString("Datoteka mora biti u formatu mp4, webm ili ogg.")));
    }

    @Test
    public void testArtistDetailView() throws Exception {
        var artist = this.createArtist();
        var collection = this.createCollection();

        var artwork = new Artwork();
        artwork.setName("Artwork");
        artwork.setDescription("Description");
        artwork.setArtist(artist);
        artwork.setCollection(collection);
        artwork = artworkRepository.save(artwork);

        mockMvc.perform(get("/artist/" + artist.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        org.hamcrest.Matchers.containsString(artwork.getName())))
                .andExpect(content().string(
                        org.hamcrest.Matchers.containsString(artwork.getDescription())))
                .andExpect(content().string(
                        org.hamcrest.Matchers.containsString(collection.getName())));

    }

    private Collection createCollection() {
        var collection = new Collection();
        collection.setName("Collection");
        collection.setDescription("Description");

        return this.collectionRepository.save(collection);
    }

    private Genre createGenre() {
        var genre = new Genre();
        genre.setName("Genre");
        genre.setDescription("Description");
        return genreRepository.save(genre);
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
