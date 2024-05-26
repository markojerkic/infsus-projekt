package hr.fer.infsus.service;

import java.io.File;

import hr.fer.infsus.forms.VideoForm;
import hr.fer.infsus.model.Artwork;
import hr.fer.infsus.model.types.Video;

public interface VideoService {

    Video saveVideo(Artwork artwork, VideoForm videoForm);

    Video getVideoById(Long id);

    File getVideoFile(Long id);

    Video getVideoByArtworkId(Long artworkId);

}
