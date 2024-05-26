package hr.fer.infsus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.infsus.model.types.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

    Optional<Video> findByArtwork_Id(Long artworkId);

}
