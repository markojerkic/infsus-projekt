package hr.fer.infsus.model.types;

import java.util.Set;

import hr.fer.infsus.model.types.sif.AudioFormat;
import hr.fer.infsus.model.types.sif.Genre;
import hr.fer.infsus.model.Artwork;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Audio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Float duration;

    @OneToOne
    private Artwork artwork;
    @ManyToMany
    private Set<Genre> genres;
    @ManyToMany
    private Set<AudioFormat> audioFormats;
}
