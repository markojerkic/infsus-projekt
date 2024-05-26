package hr.fer.infsus.service.impl;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import hr.fer.infsus.dto.artist.NewArtistDto;
import hr.fer.infsus.dto.query.ArtistQueryDto;
import hr.fer.infsus.exception.ValidationException;
import hr.fer.infsus.model.Artist;
import hr.fer.infsus.repository.ArtistRepository;
import hr.fer.infsus.repository.UserRepository;
import hr.fer.infsus.service.ArtistService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Artist> findAllArtists(Pageable pageable, ArtistQueryDto query) {
        var spec = this.search(query);
        return this.artistRepository.findAll(spec, pageable);
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
    public Artist saveArtist(Long id, NewArtistDto artistDto) {

        var artist = this.artistRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No artist with id " + id));

        artist.setType(artistDto.getType());
        artist.setName(artistDto.getName());
        artist.setLastname(artistDto.getLastname());
        artist.setUsername(artistDto.getUsername());

        return this.artistRepository.save(artist);
    }

    @Override
    public Artist getArtistById(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No artist with id " + id));
    }

    @Override
    public void deleteArtist(Long id) {
        this.artistRepository.deleteById(id);
    }

    private Specification<Artist> search(ArtistQueryDto query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (query == null) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }

            if (query.getName() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + query.getName().toLowerCase() + "%"));
            }

            if (query.getLastname() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastname")),
                        "%" + query.getLastname().toLowerCase() + "%"));
            }

            if (query.getUsername() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")),
                        "%" + query.getUsername().toLowerCase() + "%"));
            }

            if (query.getType() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("type")),
                        "%" + query.getType().toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
