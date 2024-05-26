package hr.fer.infsus.service.impl;

import hr.fer.infsus.model.Collection;
import hr.fer.infsus.repository.CollectionRepository;
import hr.fer.infsus.service.CollectionService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;

    @Override
    public Page<Collection> getAllCollections(Optional<String> name, Pageable pageable) {
        var spec = search(name);
        return this.collectionRepository.findAll(spec, pageable);
    }

    @Override
    public Collection getCollectionById(Long id) {
        return this.collectionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Collection with id " + id + " not found"));
    }

    private Specification<Collection> search(Optional<String> name) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            name.ifPresent(n -> {
                predicates.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + n.toLowerCase() + "%"));
            });
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
