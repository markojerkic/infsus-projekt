package hr.fer.infsus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.infsus.model.types.sif.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {

}
