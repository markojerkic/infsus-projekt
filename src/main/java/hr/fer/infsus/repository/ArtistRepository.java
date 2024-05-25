package hr.fer.infsus.repository;

import hr.fer.infsus.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtistRepository extends JpaRepository<Artist, Long>, JpaSpecificationExecutor<Artist> {

    @Query("SELECT a FROM Artist a WHERE lower(a.username) LIKE %?1%")
    List<Artist> findByUsername(String username);
}
