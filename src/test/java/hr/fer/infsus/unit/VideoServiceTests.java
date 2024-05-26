package hr.fer.infsus.unit;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;

import hr.fer.infsus.exception.ValidationException;
import hr.fer.infsus.forms.VideoForm;
import hr.fer.infsus.model.Artwork;
import hr.fer.infsus.model.types.Video;
import hr.fer.infsus.repository.VideoRepository;
import hr.fer.infsus.service.GenreService;
import hr.fer.infsus.service.impl.VideoServiceImpl;

public class VideoServiceTests {

    @Test
    public void testSaveVideo() {

        var videoRepository = Mockito.mock(VideoRepository.class);
        var genreService = Mockito.mock(GenreService.class);

        var videoService = new VideoServiceImpl(videoRepository, genreService);

        Mockito.when(genreService.getGenreById(1L)).thenReturn(null);
        Mockito.when(videoRepository.save(null)).thenReturn(null);

        var artwork = new Artwork();
        var videoForm = new VideoForm();

        var mockVideo = new MockMultipartFile("file.mp4", new byte[] {});
        videoForm.setFile(mockVideo);

        Assertions.assertThrows(ValidationException.class, () -> {
            videoService.saveVideo(artwork, videoForm);
        });
    }

    @Test
    public void testGetVideoFile() throws IOException {
        var videoRepository = Mockito.mock(VideoRepository.class);
        var genreService = Mockito.mock(GenreService.class);

        var videoService = new VideoServiceImpl(videoRepository, genreService);

        var tempFile = File.createTempFile("temp", ".mp4");

        var mockVideo = Video.builder().path(tempFile.getAbsolutePath()).build();

        Mockito.when(videoRepository.findById(1L)).thenReturn(Optional.of(mockVideo));

        var result = videoService.getVideoFile(1L);

        Assertions.assertEquals(tempFile, result);
        Assertions.assertEquals(tempFile.getAbsolutePath(), result.getAbsolutePath());
    }

}
