package hr.fer.infsus.repository;

import hr.fer.infsus.model.Artist;
import hr.fer.infsus.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    @Query("SELECT a from Artwork a where lower(a.name) like %?1%")
    List<Artwork> findByUsername(String name);
}
