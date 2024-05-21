package hr.fer.infsus.service.sif;

import org.springframework.stereotype.Service;

import hr.fer.infsus.model.types.sif.Genre;
import hr.fer.infsus.repository.GenreRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    public Genre createGenre(Genre genreModel) {
        var genre = Genre.builder()
                .name(genreModel.getName())
                .description(genreModel.getDescription())
                .build();

        return this.genreRepository.save(genre);
    }

    public Genre getGenreById(long genreId) {
        return this.genreRepository.findById(genreId).orElseThrow();
    }

}
