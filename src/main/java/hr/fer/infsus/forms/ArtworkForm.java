package hr.fer.infsus.forms;

import hr.fer.infsus.dto.ArtistDto;
import hr.fer.infsus.dto.ArtworkDto;
import hr.fer.infsus.forms.partial.ArtistPartial;
import hr.fer.infsus.forms.partial.CollectionPartial;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
@AllArgsConstructor
public class ArtworkForm {
    private Long id;

    private String name;

    private String description;

    private String url;

    private Long artistId;

    private Long collectionId;

    private List<ArtistPartial> artists;

    private List<CollectionPartial> collections;

    private String returnUrl;




    public ArtworkForm(String name, String description, String url, Long artistId, Long collectionId) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.artistId = artistId;
        this.collectionId = collectionId;
    }

    public ArtworkForm(List<ArtistPartial> artists, List<CollectionPartial> collections) {
        this.artists = artists;
        this.collections = collections;
    }


}
