package hr.fer.infsus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArtworkDto {
    private Long id;

    private String name;

    private String description;

    private String url;

    private Long artistId;

    private String artistUsername;

    private Long collectionId;

    private String collectionName;

    public ArtworkDto(Long id, String name, String url, Long artistId, String artistUsername, Long collectionId,
            String collectionName) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.artistId = artistId;
        this.artistUsername = artistUsername;
        this.collectionId = collectionId;
        this.collectionName = collectionName;
    }
}
