package hr.fer.infsus.service.impl;

import hr.fer.infsus.dto.ArtistDto;
import hr.fer.infsus.dto.ArtworkDto;
import hr.fer.infsus.forms.partial.ArtistPartial;
import hr.fer.infsus.model.Artist;
import hr.fer.infsus.repository.ArtisRepository;
import hr.fer.infsus.service.ArtistService;
import hr.fer.infsus.service.ArtworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtisRepository artisRepository;
    private final ArtworkServiceImpl artworkService;



    @Override
    public List<ArtistDto> findAllArtists() {
       List<Artist> artists = artisRepository.findAll();
       return artists.stream()
               .map(this::mapToArtistDto)
               .collect(Collectors.toList());
    }

    @Override
    public Long createArtist(ArtistDto artist) {
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

    @Override
    public Long saveArtist(ArtistDto artist) {
        Artist artist1 = artisRepository.findById(artist.getId()).orElseThrow(() -> new IllegalStateException("No artist with id " + artist.getId()));

        artist1.setType(artist.getType());
        artist1.setName(artist.getName());
        artist1.setLastname(artist.getLastname());
        artist1.setUsername(artist.getUsername());

        artisRepository.save(artist1);

        return artist1.getId();
    }

    @Override
    public ArtistDto findById(Long id) {
        return this.mapToArtistDto(artisRepository.findById(id).orElseThrow(() -> new IllegalStateException("No artist with id " + id)));
    }

    @Override
    public void deleteArtist(Long id) {
        artisRepository.deleteById(id);
    }

    @Override
    public List<ArtistPartial> allArtistPartials() {
        return findAllArtists().stream()
                .map(this::mapToArtistPartial)
                .collect(Collectors.toList());
    }

    @Override
    public ArtistDto findArtistById(Long id) {
        Artist artist = artisRepository.findById(id).orElseThrow(() -> new IllegalStateException("No artist with id " + id));
        return mapToArtistDto(artist);
    }

    private ArtistPartial mapToArtistPartial(ArtistDto artistDto) {
        return new ArtistPartial(artistDto.getId(), artistDto.getUsername());
    }

    private ArtistDto mapToArtistDto(Artist artist) {
        List<ArtworkDto> artworks = artist.getArtworks().stream().map(artworkService::mapToDto).toList();
        return new ArtistDto(
                artist.getId(),
                artist.getName(),
                artist.getLastname(),
                artist.getUsername(),
                artist.getType(),
                artworks
        );
    }
}
