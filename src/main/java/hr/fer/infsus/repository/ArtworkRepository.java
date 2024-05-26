package hr.fer.infsus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import hr.fer.infsus.model.Artwork;

public interface ArtworkRepository extends JpaRepository<Artwork, Long>, JpaSpecificationExecutor<Artwork> {

    @Query("SELECT a from Artwork a where lower(a.name) like %?1%")
    List<Artwork> findByUsername(String name);
}
