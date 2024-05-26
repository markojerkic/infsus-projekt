package hr.fer.infsus.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoForm {
    @NotBlank(message = "Trajanje videa ne smije biti prazano")
    @Pattern(regexp = "^\\d+[smhdwmy]$", message = "Trajanje mora biti oblika <broj><mjerna jedinica (npr. s, m, h, d, w, m, y)>")
    private String duration;

    @NotNull(message = "Žanr ne smije biti prazan")
    private Long genreId;

    @NotNull(message = "Datoteka videa ne smije biti prazna")
    private MultipartFile file;
}
