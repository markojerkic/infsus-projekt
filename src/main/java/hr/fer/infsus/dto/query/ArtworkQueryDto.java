package hr.fer.infsus.dto.query;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ArtworkQueryDto {
    private String name;
    private String description;
    private String collectionName;
}
