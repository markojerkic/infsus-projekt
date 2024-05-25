package hr.fer.infsus.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hr.fer.infsus.dto.artist.ArtistDto;
import hr.fer.infsus.dto.artist.NewArtistDto;
import hr.fer.infsus.dto.query.ArtistQueryDto;
import hr.fer.infsus.model.Artist;

public interface ArtistService {
    Page<Artist> findAllArtists(Pageable pageable, ArtistQueryDto query);

    Artist getArtistById(Long id);

    Long createArtist(NewArtistDto artist);

    Long saveArtist(ArtistDto artistDto);

    void deleteArtist(Long id);

}
