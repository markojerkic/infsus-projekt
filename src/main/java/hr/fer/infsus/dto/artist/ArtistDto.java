package hr.fer.infsus.dto.artist;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ArtistDto {
    private Long id;

    @NotBlank(message = "Ime umjetnika ne smije biti prazno")
    private String name;
    @NotBlank(message = "Prezime umjetnika ne smije biti prazno")
    private String lastname;
    @NotBlank(message = "Tip umjetnika ne smije biti prazno")
    private String type;

}
