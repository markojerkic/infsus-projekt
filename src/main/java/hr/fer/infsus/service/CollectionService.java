package hr.fer.infsus.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hr.fer.infsus.dto.query.CollectionQueryDto;
import hr.fer.infsus.model.Collection;

public interface CollectionService {
    Page<Collection> getAllCollections(CollectionQueryDto query, Pageable pageable);

    Collection saveCollection(Collection collection);

    Collection updateCollection(Long id, Collection collection);

    Collection getCollectionById(Long id);

    void deleteCollection(Long id);
}
