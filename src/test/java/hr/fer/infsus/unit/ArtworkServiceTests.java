package hr.fer.infsus.unit;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import hr.fer.infsus.forms.ArtworkForm;
import hr.fer.infsus.model.Artist;
import hr.fer.infsus.model.Artwork;
import hr.fer.infsus.model.Collection;
import hr.fer.infsus.repository.ArtworkRepository;
import hr.fer.infsus.service.ArtistService;
import hr.fer.infsus.service.CollectionService;
import hr.fer.infsus.service.VideoService;
import hr.fer.infsus.service.impl.ArtworkServiceImpl;

public class ArtworkServiceTests {

    @Test
    public void testSaveArtwork() {
        var artworkRepository = Mockito.mock(ArtworkRepository.class);
        var artistService = Mockito.mock(ArtistService.class);
        var collectionService = Mockito.mock(CollectionService.class);
        var videoService = Mockito.mock(VideoService.class);

        var mockArtist = new Artist();
        mockArtist.setArtworks(new ArrayList<>());
        Mockito.when(artistService.getArtistById(1L)).thenReturn(mockArtist);

        var mockCollection = new Collection();
        Mockito.when(collectionService.getCollectionById(1L)).thenReturn(mockCollection);

        Mockito.when(videoService.saveVideo(Mockito.any(), Mockito.any())).thenReturn(null);

        Mockito.when(artworkRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(artworkRepository.findById(Mockito.any()))
                .thenAnswer(invocation -> {
                    var artwork = new Artwork();
                    artwork.setId(invocation.getArgument(0));
                    return Optional.of(artwork);
                });

        var artworkService = new ArtworkServiceImpl(
                artworkRepository,
                artistService,
                collectionService,
                videoService);

        var artworkForm = ArtworkForm.builder()
                .name("Artwork")
                .description("Description")
                .artistId(1L)
                .collectionId(1L)
                .build();

        var savedArtwork = artworkService.saveArtwork(52L, artworkForm);

        Mockito.verify(artworkRepository).save(Mockito.any());

        Assertions.assertEquals(artworkForm.getName(), savedArtwork.getName());
        Assertions.assertEquals(artworkForm.getDescription(), savedArtwork.getDescription());
        Assertions.assertEquals(mockArtist, savedArtwork.getArtist());

        Assertions.assertEquals(52L, savedArtwork.getId());
    }

    @Test
    public void testCreateArtwork() {
        var artworkRepository = Mockito.mock(ArtworkRepository.class);
        var artistService = Mockito.mock(ArtistService.class);
        var collectionService = Mockito.mock(CollectionService.class);
        var videoService = Mockito.mock(VideoService.class);

        var mockArtist = new Artist();
        mockArtist.setArtworks(new ArrayList<>());
        Mockito.when(artistService.getArtistById(1L)).thenReturn(mockArtist);

        var mockCollection = new Collection();
        Mockito.when(collectionService.getCollectionById(1L)).thenReturn(mockCollection);

        Mockito.when(videoService.saveVideo(Mockito.any(), Mockito.any())).thenReturn(null);

        Mockito.when(artworkRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        var artworkService = new ArtworkServiceImpl(
                artworkRepository,
                artistService,
                collectionService,
                videoService);

        var artworkForm = ArtworkForm.builder()
                .name("Artwork")
                .description("Description")
                .artistId(1L)
                .collectionId(1L)
                .build();

        var savedArtwork = artworkService.createArtwork(artworkForm);

        Assertions.assertEquals(artworkForm.getName(), savedArtwork.getName());
        Assertions.assertEquals(artworkForm.getDescription(), savedArtwork.getDescription());
        Assertions.assertEquals(mockArtist, savedArtwork.getArtist());

    }

}
