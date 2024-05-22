package hr.fer.infsus.service.sif.impl;

import hr.fer.infsus.dto.ArtistDto;
import hr.fer.infsus.model.Artist;
import hr.fer.infsus.repository.ArtisRepository;
import hr.fer.infsus.repository.UserRepository;
import hr.fer.infsus.service.sif.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtisRepository artisRepository;
    private final UserRepository userRepository;


    @Override
    public List<ArtistDto> findAllArtists() {
       List<Artist> artists = artisRepository.findAll();
       return artists.stream()
               .map(this::mapToArtistDto)
               .collect(Collectors.toList());
    }

    @Override
    public Long saveArtist(ArtistDto artist) {
        Artist newArtist = new Artist(
                artist.getName(),
                artist.getLastname(),
                artist.getUsername(),
                artist.getType()
        );
       newArtist = artisRepository.save(newArtist);

//          Long id = userRepository.save(newArtist).getId();
       return newArtist.getId();

    }

    private ArtistDto mapToArtistDto(Artist artist) {
        return new ArtistDto(
                artist.getId(),
                artist.getName(),
                artist.getLastname(),
                artist.getUsername(),
                artist.getType()
        );
    }
}
