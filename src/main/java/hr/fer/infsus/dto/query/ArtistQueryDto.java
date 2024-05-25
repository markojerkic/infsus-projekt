package hr.fer.infsus.dto.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistQueryDto {
    private String name;
    private String lastname;
    private String username;
    private String type;
}
