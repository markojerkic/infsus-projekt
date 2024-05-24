package hr.fer.infsus.forms;

import hr.fer.infsus.dto.ArtistDto;
import hr.fer.infsus.dto.ArtworkDto;
import hr.fer.infsus.forms.partial.ArtistPartial;
import hr.fer.infsus.forms.partial.CollectionPartial;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtworkForm {
    private Long id;

    @NotBlank(message = "Naziv djela ne smije biti prazan")
    private String name;

    @NotBlank(message = "Opis djela ne smije biti prazan")
    private String description;


    @Pattern(regexp = "http.?://s3.amazonaws.com/.+/.+", message = "URL mora voditi na Amazon AWS S3 bucket i biti oblika http.?://s3.amazonaws.com/.+/.+")
    private String url;

//    @NotBlank(message = "ID umjetnika ne smije biti prazan")
    private Long artistId;

    private String artistUsername;


    private Long collectionId;

    private String collectionName;


    private List<ArtistPartial> artists;

    private List<CollectionPartial> collections;

    private String returnUrl;





//    public ArtworkForm(String name, String description, String url, Long artistId, Long collectionId) {
//        this.name = name;
//        this.description = description;
//        this.url = url;
//        this.artistId = artistId;
//        this.collectionId = collectionId;
//    }


//    public ArtworkForm(String name, String description, String url, Long artistId, Long collectionId, List<ArtistPartial> artists, List<CollectionPartial> collections) {
//        this.artists = artists;
//        this.collections = collections;
//        this.name = name;
//        this.description = description;
//        this.url = url;
//        this.artistId = artistId;
//        this.collectionId = collectionId;
//    }


    public ArtworkForm(Long id, String name, String description, String url, Long artistId, Long collectionId, List<ArtistPartial> artistPartials, List<CollectionPartial> collectionPartials, String returnUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.artistId = artistId;
        this.collectionId = collectionId;
        this.artists = artistPartials;
        this.collections = collectionPartials;
        this.returnUrl = returnUrl;
    }
}
