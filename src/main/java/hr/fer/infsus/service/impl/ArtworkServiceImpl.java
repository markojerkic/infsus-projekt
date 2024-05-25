package hr.fer.infsus.service.impl;

import hr.fer.infsus.dto.ArtworkDto;
import hr.fer.infsus.forms.ArtworkForm;
import hr.fer.infsus.model.Artist;
import hr.fer.infsus.model.Artwork;
import hr.fer.infsus.model.Collection;
import hr.fer.infsus.repository.ArtistRepository;
import hr.fer.infsus.repository.ArtworkRepository;
import hr.fer.infsus.repository.CollectionRepository;
import hr.fer.infsus.service.ArtworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtworkServiceImpl implements ArtworkService {

    private final ArtworkRepository repository;
    private final ArtistRepository artisRepository;
    private final CollectionRepository collectionRepository;
    @Override
    public List<ArtworkDto> findAllArtworks() {
        return repository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long createArtwork(ArtworkForm artworkForm) {
        Artist artist = artisRepository.findById(artworkForm.getArtistId()).orElseThrow(()-> new IllegalStateException("No artist with id " + artworkForm.getArtistId()));
        Collection collection = null;

        if(artworkForm.getCollectionId() != null){
            collection = collectionRepository.findById(artworkForm.getCollectionId()).orElseThrow(()-> new IllegalStateException("No collcetion with id " + artworkForm.getCollectionId()));
        }


        Artwork artwork = new Artwork(
                artworkForm.getName(),
                artworkForm.getDescription(),
                artist,
                collection,
                artworkForm.getUrl()
        );

        artist.getArtworks().add(artwork);

        if(collection != null){
            collection.getArtworks().add(artwork);
        }

        artwork = repository.save(artwork);
        return artwork.getId();
    }

    @Override
    public Long saveArtwork(ArtworkForm artworkForm) {
        Artwork artwork = repository.findById(artworkForm.getId()).orElseThrow(() -> new IllegalStateException("No artwork with id " + artworkForm.getId()));
        Artist artist = artisRepository.findById(artworkForm.getArtistId()).orElseThrow(() -> new IllegalStateException("No artist with id " + artworkForm.getArtistId()));

        Collection collection = null;
        if(artworkForm.getCollectionId() != null){
            collection = collectionRepository.findById(artworkForm.getCollectionId()).orElseThrow(() -> new IllegalStateException("No collecetion with id " + artworkForm.getCollectionId()));
        }


//        artwork.getArtist().getArtworks().remove(artwork);
//        artwork.getArtist().getArtworks().add(artwork);
        artwork.setArtist(artist);

        if(artwork.getCollection() != null){
            artwork.getCollection().getArtworks().remove(artwork);
        }

        artwork.setCollection(collection);
        artwork.setName(artworkForm.getName());
        artwork.setUrl(artworkForm.getUrl());
        artwork.setDescription(artworkForm.getDescription());

        repository.save(artwork);

        return artworkForm.getId();
    }

    @Override
    public ArtworkDto findById(Long id) {
        return mapToDto(repository.findById(id).orElseThrow(() -> new IllegalStateException("No artwork with id " + id)));
    }

    @Override
    public void deleteArtwork(Long id) {
        Artwork artwork = repository.findById(id).orElseThrow(() -> new IllegalStateException("No artwork with id " + id));
        repository.delete(artwork);
    }

    @Override
    public List<ArtworkDto> findByName(String s) {
        return repository.findByUsername(s.toLowerCase(Locale.ROOT)).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public ArtworkDto mapToDto(Artwork artwork) {
        return new ArtworkDto(
                artwork.getId(),
                artwork.getName(),
                artwork.getDescription(),
                artwork.getUrl(),
                artwork.getArtist().getId(),
                artwork.getArtist().getUsername(),
                artwork.getCollection() == null ? null : artwork.getCollection().getId(),
                artwork.getCollection() == null ? null : artwork.getCollection().getName()
        );
    }
}
