package hr.fer.infsus.dto;

import hr.fer.infsus.model.Artwork;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Ime umjetnika ne smije biti prazno")
    private String name;
    @NotBlank(message = "Prezime umjetnika ne smije biti prazno")
    private String lastname;
    @NotBlank(message = "Korisnicko ime umjetnika ne smije biti prazno")
    private String username;
    @NotBlank(message = "Tip umjetnika ne smije biti prazno")
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
