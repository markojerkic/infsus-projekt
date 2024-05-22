package hr.fer.infsus.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArtistDto {
    private Long id;
    private String name;

    private String lastname;

    private String username;

    private String type;

    public ArtistDto(String name, String lastname, String username, String type) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.type = type;
    }
}
