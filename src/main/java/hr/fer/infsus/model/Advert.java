package hr.fer.infsus.model;

import java.util.List;

import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Advert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private float price;

    @Type(ListArrayType.class)
    @Column(columnDefinition = "TEXT[]")
    private List<String> tags;

    @ManyToOne
    private Artist artist;
    @ManyToOne
    private Artwork artwork;

}
