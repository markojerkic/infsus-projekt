package hr.fer.infsus.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    public Artist(String name, String lastname, String username, String type) {
        super(name, lastname, username);
        this.type = type;
    }
}
