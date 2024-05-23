package hr.fer.infsus.service.sif;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import hr.fer.infsus.model.types.sif.Genre;
import hr.fer.infsus.repository.GenreRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public Genre updateGenre(Long id, Genre genreModel) {
        this.genreRepository.findById(id).orElseThrow();

        var genre = Genre.builder()
                .id(id)
                .name(genreModel.getName())
                .description(genreModel.getDescription())
                .build();

        return this.genreRepository.save(genre);
    }

    public Genre createGenre(Genre genreModel) {
        var genre = Genre.builder()
                .name(genreModel.getName())
                .description(genreModel.getDescription())
                .build();

        return this.genreRepository.save(genre);
    }

    public void deleteGenre(long genreId) {
        this.genreRepository.deleteById(genreId);
    }

    public Genre getGenreById(long genreId) {
        return this.genreRepository.findById(genreId).orElseThrow();
    }

    public Page<Genre> getGenres(Pageable pageable,
            Optional<String> name,
            Optional<String> description) {

        return this.genreRepository.findAll(this.buildSearch(name, description), pageable);
    }

    public Specification<Genre> buildSearch(Optional<String> name, Optional<String> description) {
        return (root, query, criteriaBuilder) -> {
            var predicates = new ArrayList<Predicate>();

            name.ifPresent(n -> predicates
                    .add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + n.toLowerCase() + "%")));

            description.ifPresent(d -> predicates
                    .add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                            "%" + d.toLowerCase() + "%")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
