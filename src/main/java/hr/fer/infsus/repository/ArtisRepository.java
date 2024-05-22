package hr.fer.infsus.repository;

import hr.fer.infsus.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtisRepository extends JpaRepository<Artist, Long> {
}
