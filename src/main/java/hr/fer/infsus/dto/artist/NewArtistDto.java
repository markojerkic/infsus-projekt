package hr.fer.infsus.dto.artist;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class NewArtistDto extends ArtistDto {
    @NotBlank(message = "Korisnicko ime umjetnika ne smije biti prazno")
    private String username;

}
