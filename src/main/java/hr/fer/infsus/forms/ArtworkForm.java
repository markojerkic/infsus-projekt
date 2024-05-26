package hr.fer.infsus.forms;

import hr.fer.infsus.model.Artwork;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtworkForm {
    private Long id;

    @NotBlank(message = "Naziv djela ne smije biti prazan")
    private String name;

    @NotBlank(message = "Opis djela ne smije biti prazan")
    private String description;

    @NotNull(message = "ID umjetnika ne smije biti prazan")
    private Long artistId;

    private Long collectionId;

    @NotNull
    @Valid
    private VideoForm video;

    public ArtworkForm(Artwork artwork) {
        this.id = artwork.getId();
        this.name = artwork.getName();
        this.description = artwork.getDescription();
        this.artistId = artwork.getArtist().getId();
        if (artwork.getCollection() != null) {
            this.collectionId = artwork.getCollection().getId();
        }
    }

}
