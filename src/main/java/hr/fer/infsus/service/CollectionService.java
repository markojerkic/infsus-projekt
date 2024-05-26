package hr.fer.infsus.service;

import hr.fer.infsus.model.Collection;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollectionService {
    Page<Collection> getAllCollections(Optional<String> name, Pageable pageable);

    Collection getCollectionById(Long id);
}
