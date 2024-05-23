package hr.fer.infsus.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true, nullable = false)
    private String username;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Artist> savedArtists;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Artwork> savedArtworks;

    public User(String name, String lastname, String username) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        savedArtworks = new HashSet<>();
        savedArtists = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
