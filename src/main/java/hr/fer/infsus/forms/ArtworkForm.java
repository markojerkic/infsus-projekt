package hr.fer.infsus.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    //@Pattern(regexp = "http.?://s3.amazonaws.com/.+/.+", message = "URL mora voditi na Amazon AWS S3 bucket i biti oblika http.?://s3.amazonaws.com/.+/.+")
    private String url;

    @NotNull(message = "ID umjetnika ne smije biti prazan")
    private Long artistId;

    private Long collectionId;
}
