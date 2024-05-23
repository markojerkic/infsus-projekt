package hr.fer.infsus.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Artist extends User{
//    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @OneToOne(optional = false)
//    private User user;
    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Artwork> artworks;

    public Artist(String name, String lastname, String username, String type) {
        super(name, lastname, username);
        this.type = type;
        this.artworks = new ArrayList<>();
    }
}
