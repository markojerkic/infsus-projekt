package hr.fer.infsus.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hr.fer.infsus.model.types.sif.Genre;

public interface GenreService {

    Genre updateGenre(Long id, Genre genreModel);

    Genre createGenre(Genre genreModel);

    void deleteGenre(long genreId);

    Genre getGenreById(long genreId);

    Page<Genre> getGenres(Pageable pageable,
            Optional<String> name,
            Optional<String> description);
}
