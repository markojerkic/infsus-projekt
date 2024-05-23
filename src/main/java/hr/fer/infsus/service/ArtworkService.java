package hr.fer.infsus.service;

import hr.fer.infsus.dto.ArtworkDto;

import java.util.List;

public interface ArtworkService {
    List<ArtworkDto> findAllArtworks();

    Long saveArtwork(ArtworkDto artworkDto);

    Long createArtwork(ArtworkDto artworkDto);

    ArtworkDto findById(Long id);

    void deleteArtwork(Long id);
}
