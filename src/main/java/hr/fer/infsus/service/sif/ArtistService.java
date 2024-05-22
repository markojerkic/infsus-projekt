package hr.fer.infsus.service.sif;

import hr.fer.infsus.dto.ArtistDto;
import hr.fer.infsus.model.Artist;

import java.util.List;

public interface ArtistService {
    List<ArtistDto> findAllArtists();

    Long saveArtist(ArtistDto artist);
}
