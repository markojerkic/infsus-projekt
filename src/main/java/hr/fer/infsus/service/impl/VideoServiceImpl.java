package hr.fer.infsus.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import hr.fer.infsus.exception.ValidationException;
import hr.fer.infsus.forms.VideoForm;
import hr.fer.infsus.model.Artwork;
import hr.fer.infsus.model.types.Video;
import hr.fer.infsus.repository.VideoRepository;
import hr.fer.infsus.service.VideoService;
import hr.fer.infsus.service.sif.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final GenreService genreService;

    @Override
    public Video saveVideo(Artwork artwork, VideoForm videoForm) {
        this.checkVideoIsCorrectFormat(videoForm);

        var video = Video.builder()
                .artwork(artwork)
                .duration(this.parseStringDurationToMs(videoForm.getDuration()))
                .genres(Set.of(this.genreService.getGenreById(videoForm.getGenreId())))
                .build();

        this.saveVideoFile(videoForm).ifPresent(video::setPath);

        return video;
    }

    @Override
    public Video getVideoById(Long id) {
        return this.videoRepository.findById(id).orElseThrow();
    }

    @Override
    public File getVideoFile(Long id) {
        var video = this.getVideoById(id);

        return new File(video.getPath());
    }

    private void checkVideoIsCorrectFormat(VideoForm videoForm) {
        var videoFileName = videoForm.getFile().getOriginalFilename();
        var allowedExtensions = List.of("mp4", "webm", "ogg");

        allowedExtensions.stream().filter(videoFileName::contains).findAny().orElseThrow(() -> {
            log.error("Video is in format {}, but only mp4, webm and ogg are allowed.", videoFileName);
            return new ValidationException("video.file", "Datoteka mora biti u formatu mp4, webm ili ogg.");
        });
    }

    /**
     * @Param duration - duration in format 1s, 1m, 1h, 1d
     * @return duration in milliseconds
     */
    private Float parseStringDurationToMs(String duration) {
        if (duration == null) {
            return null;
        }

        var timeUnit = duration.substring(duration.length() - 1);
        var timeValue = Float.parseFloat(duration.substring(0, duration.length() - 1));

        switch (timeUnit) {
            case "s":
                return timeValue * 1000;
            case "m":
                return timeValue * 1000 * 60;
            case "h":
                return timeValue * 1000 * 60 * 60;
            case "d":
                return timeValue * 1000 * 60 * 60 * 24;
            default:
                return null;
        }

    }

    private Optional<String> saveVideoFile(VideoForm videoForm) {
        var file = videoForm.getFile();

        try {
            var destinationFile = File.createTempFile("video", file.getOriginalFilename());

            file.transferTo(destinationFile);

            return Optional.of(destinationFile.getAbsolutePath());
        } catch (IOException e) {
            log.error("Error while saving video file", e);
            throw new ValidationException("video.file", "Greška prilikom spremanja datoteke.");
        }

    }

}
