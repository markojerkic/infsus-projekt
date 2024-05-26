package hr.fer.infsus.service.impl;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import hr.fer.infsus.dto.query.ArtworkQueryDto;
import hr.fer.infsus.forms.ArtworkForm;
import hr.fer.infsus.model.Artist;
import hr.fer.infsus.model.Artwork;
import hr.fer.infsus.model.Collection;
import hr.fer.infsus.repository.ArtistRepository;
import hr.fer.infsus.repository.ArtworkRepository;
import hr.fer.infsus.repository.CollectionRepository;
import hr.fer.infsus.service.ArtistService;
import hr.fer.infsus.service.ArtworkService;
import hr.fer.infsus.service.CollectionService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtworkServiceImpl implements ArtworkService {

    private final ArtworkRepository artworkRepository;
    private final ArtistService artistService;
    private final CollectionService collectionService;

    @Override
    public Page<Artwork> findAllArtworks(Long artistId, Pageable pageable, ArtworkQueryDto query) {
        var spec = search(query);

        spec = spec.and((root, criteriaQuery, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("artist").get("id"), artistId);
        });

        return this.artworkRepository.findAll(spec, pageable);
    }

    @Override
    public Page<Artwork> findAllArtworks(Pageable pageable, ArtworkQueryDto query) {
        var spec = search(query);
        return this.artworkRepository.findAll(spec, pageable);
    }

    @Override
    public Artwork createArtwork(ArtworkForm artworkForm) {
        var artist = this.artistService.getArtistById(artworkForm.getArtistId());
        var collection = this.collectionService.getCollectionById(artworkForm.getCollectionId());

        var artwork = new Artwork(
                artworkForm.getName(),
                artworkForm.getDescription(),
                artist,
                collection,
                artworkForm.getUrl());

        artist.getArtworks().add(artwork);

        return this.artworkRepository.save(artwork);
    }

    @Override
    public Artwork saveArtwork(ArtworkForm artworkForm) {
        return null;

        // Artwork artwork = artworkRepository.findById(artworkForm.getId())
        // .orElseThrow(() -> new IllegalStateException("No artwork with id " +
        // artworkForm.getId()));
        // Artist artist = artisRepository.findById(artworkForm.getArtistId())
        // .orElseThrow(() -> new IllegalStateException("No artist with id " +
        // artworkForm.getArtistId()));
        //
        // Collection collection = null;
        // if (artworkForm.getCollectionId() != null) {
        // collection =
        // collectionRepository.findById(artworkForm.getCollectionId()).orElseThrow(
        // () -> new IllegalStateException("No collecetion with id " +
        // artworkForm.getCollectionId()));
        // }
        //
        //// artwork.getArtist().getArtworks().remove(artwork);
        //// artwork.getArtist().getArtworks().add(artwork);
        // artwork.setArtist(artist);
        //
        // if (artwork.getCollection() != null) {
        // artwork.getCollection().getArtworks().remove(artwork);
        // }
        //
        // artwork.setCollection(collection);
        // artwork.setName(artworkForm.getName());
        // artwork.setUrl(artworkForm.getUrl());
        // artwork.setDescription(artworkForm.getDescription());
        //
        // artworkRepository.save(artwork);
        //
        // return artworkForm.getId();
    }

    @Override
    public Artwork getById(Long id) {
        return this.artworkRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No artwork with id " + id));
    }

    @Override
    public void deleteArtwork(Long id) {
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No artwork with id " + id));
        artworkRepository.delete(artwork);
    }

    private Specification<Artwork> search(ArtworkQueryDto query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            if (query == null) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }

            if (query.getName() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + query.getName().toLowerCase() + "%"));
            }

            if (query.getDescription() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                        "%" + query.getDescription().toLowerCase() + "%"));
            }

            if (query.getCollectionName() != null) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("collection").get("name")),
                        "%" + query.getCollectionName().toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
