package hr.fer.infsus.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hr.fer.infsus.dto.query.ArtworkQueryDto;
import hr.fer.infsus.forms.ArtworkForm;
import hr.fer.infsus.model.Artwork;

public interface ArtworkService {
    Page<Artwork> findAllArtworks(Long artistId, Pageable pageable, ArtworkQueryDto query);

    Page<Artwork> findAllArtworks(Pageable pageable, ArtworkQueryDto query);

    Artwork getById(Long id);

    Artwork saveArtwork(ArtworkForm artworkForm);

    Artwork createArtwork(ArtworkForm artworkForm);

    void deleteArtwork(Long id);
}
