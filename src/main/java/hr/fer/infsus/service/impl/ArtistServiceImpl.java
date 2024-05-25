package hr.fer.infsus.service.impl;

import hr.fer.infsus.dto.ArtworkDto;
import hr.fer.infsus.dto.artist.ArtistDto;
import hr.fer.infsus.dto.artist.ArtistWithArtwork;
import hr.fer.infsus.dto.artist.NewArtistDto;
import hr.fer.infsus.exception.ValidationException;
import hr.fer.infsus.forms.partial.ArtistPartial;
import hr.fer.infsus.model.Artist;
import hr.fer.infsus.repository.ArtistRepository;
import hr.fer.infsus.repository.UserRepository;
import hr.fer.infsus.service.ArtistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtworkServiceImpl artworkService;
    private final UserRepository userRepository;

    @Override
    public List<ArtistDto> findAllArtists() {
        List<Artist> artists = artistRepository.findAll();
        return artists.stream()
                .map(this::mapToArtistDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long createArtist(NewArtistDto artistDto) {
        this.userRepository.findByUsername(artistDto.getUsername()).ifPresent(user -> {
            log.warn("User with username {} already exists", artistDto.getUsername());
            throw new ValidationException("username", "Korisnik s korisničkim imenom već postoji");
        });

        if (artistDto.getUsername().contains(" ")) {
            log.warn("Username cannot contain spaces");
            throw new ValidationException("username", "Korisničko ime ne smije sadržavati razmake");
        }

        this.artistRepository.findByUsername(artistDto.getUsername());

        Artist newArtist = new Artist(
                artistDto.getName(),
                artistDto.getLastname(),
                artistDto.getUsername(),
                artistDto.getType());
        newArtist = this.artistRepository.save(newArtist);

        return newArtist.getId();
    }

    @Override
    public Long saveArtist(ArtistDto artistDto) {
        var artist = new Artist();
        artist.setType(artist.getType());
        artist.setName(artist.getName());
        artist.setLastname(artist.getLastname());
        artist.setUsername(artist.getUsername());

        this.artistRepository.save(artist);

        return artist.getId();
    }

    @Override
    public ArtistDto findById(Long id) {
        return this.mapToArtistDto(
                artistRepository.findById(id).orElseThrow(() -> new IllegalStateException("No artist with id " + id)));
    }

    @Override
    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }

    @Override
    public List<ArtistPartial> allArtistPartials() {
        return this.findAllArtists()
                .stream()
                .map(this::mapToArtistPartial)
                .collect(Collectors.toList());
    }

    @Override
    public ArtistDto findArtistById(Long id, String query) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No artist with id " + id));
        return mapToArtistDto(artist, query);
    }

    @Override
    public List<ArtistDto> findByUsername(String query) {
        return this.artistRepository.findByUsername(query.toLowerCase()).stream().map(this::mapToArtistDto)
                .collect(Collectors.toList());
    }

    private ArtistPartial mapToArtistPartial(NewArtistDto artistDto) {
        return new ArtistPartial(artistDto.getId(), artistDto.etUsername());
    }

    private ArtistWithArtwork mapToArtistDto(Artist artist, String query) {
        List<ArtworkDto> artworks;
        if (query == null) {
            artworks = artist.getArtworks().stream().map(artworkService::mapToDto).toList();
        } else {
            artworks = artist.getArtworks().stream()
                    .filter(artwork -> artwork.getName().toLowerCase().contains(query.toLowerCase()))
                    .map(artworkService::mapToDto)
                    .toList();
        }

        var artistWithArtwork = new ArtistWithArtwork(artworks);
        artistWithArtwork.setId(artist.getId());
        artistWithArtwork.setName(artist.getName());
        artistWithArtwork.setLastname(artist.getLastname());
        artistWithArtwork.setUsername(artist.getUsername());

        return artistWithArtwork;
    }
}
