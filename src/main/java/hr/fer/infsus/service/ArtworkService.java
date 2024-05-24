package hr.fer.infsus.service;

import hr.fer.infsus.dto.ArtworkDto;
import hr.fer.infsus.forms.ArtworkForm;

import java.util.List;

public interface ArtworkService {
    List<ArtworkDto> findAllArtworks();

    Long saveArtwork(ArtworkForm artworkForm);

    Long createArtwork(ArtworkForm artworkForm);

    ArtworkDto findById(Long id);

    void deleteArtwork(Long id);

    List<ArtworkDto> findByName(String s);
}
