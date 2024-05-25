package hr.fer.infsus.dto.artist;

import java.util.List;

import hr.fer.infsus.dto.ArtworkDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ArtistWithArtwork extends NewArtistDto {

    private final List<ArtworkDto> artworks;



}
