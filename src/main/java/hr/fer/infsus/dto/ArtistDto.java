package hr.fer.infsus.dto;

import hr.fer.infsus.model.Artwork;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArtistDto {
    private Long id;
    private String name;

    private String lastname;

    private String username;

    private String type;

    private List<ArtworkDto> artworks;

    public ArtistDto(Long id, String name, String lastname, String username, String type) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.type = type;
        this.artworks = new ArrayList<>();
    }


}
