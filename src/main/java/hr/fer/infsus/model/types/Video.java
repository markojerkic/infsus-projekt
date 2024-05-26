package hr.fer.infsus.model.types;

import java.util.Set;

import hr.fer.infsus.model.Artwork;
import hr.fer.infsus.model.types.sif.Genre;
import hr.fer.infsus.model.types.sif.VideoFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Float duration;

    @Column(nullable = false)
    private String path;

    @OneToOne
    private Artwork artwork;
    @ManyToMany
    private Set<Genre> genres;
    @ManyToMany
    private Set<VideoFormat> videoFormats;
}
