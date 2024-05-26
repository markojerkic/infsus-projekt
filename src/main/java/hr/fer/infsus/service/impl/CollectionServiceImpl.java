package hr.fer.infsus.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import hr.fer.infsus.dto.query.CollectionQueryDto;
import hr.fer.infsus.model.Collection;
import hr.fer.infsus.repository.CollectionRepository;
import hr.fer.infsus.service.CollectionService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;

    @Override
    public Page<Collection> getAllCollections(CollectionQueryDto query, Pageable pageable) {
        var spec = search(query);
        return this.collectionRepository.findAll(spec, pageable);
    }

    @Override
    public Collection getCollectionById(Long id) {
        return this.collectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Collection with id " + id + " not found"));
    }

    @Override
    public Collection saveCollection(Collection collection) {
        var col = Collection.builder()
                .name(collection.getName())
                .description(collection.getDescription())
                .build();

        return this.collectionRepository.save(col);
    }

    @Override
    public Collection updateCollection(Long id, Collection collection) {
        var col = this.getCollectionById(id);
        col.setName(collection.getName());
        col.setDescription(collection.getDescription());

        return this.collectionRepository.save(col);
    }

    @Override
    public void deleteCollection(Long id) {
        this.collectionRepository.deleteById(id);
    }

    private Specification<Collection> search(CollectionQueryDto search) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (search.getName() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + search.getName() + "%"));
            }

            if (search.getDescription() != null) {
                predicates.add(criteriaBuilder.like(root.get("description"), "%" + search.getDescription() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
