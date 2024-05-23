package hr.fer.infsus.service;

import hr.fer.infsus.dto.ArtistDto;
import hr.fer.infsus.forms.partial.ArtistPartial;
import hr.fer.infsus.model.Artist;

import java.util.List;

public interface ArtistService {
    List<ArtistDto> findAllArtists();

    Long createArtist(ArtistDto artist);

    Long saveArtist(ArtistDto artistDto);

    ArtistDto findById(Long id);

    void deleteArtist(Long id);

    List<ArtistPartial> allArtistPartials();

    ArtistDto findArtistById(Long id);
}
