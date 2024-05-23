package hr.fer.infsus.controller;

import hr.fer.infsus.dto.ArtworkDto;
import hr.fer.infsus.forms.ArtworkForm;
import hr.fer.infsus.forms.partial.ArtistPartial;
import hr.fer.infsus.forms.partial.CollectionPartial;
import hr.fer.infsus.service.ArtistService;
import hr.fer.infsus.service.ArtworkService;
import hr.fer.infsus.service.CollectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/artwork")
@RequiredArgsConstructor
@Slf4j
public class ArtworkController {

    private final ArtworkService artworkService;
    private final ArtistService artistService;
    private final CollectionService collectionService;

    @GetMapping
    public String allArtwork(Model model){
        List<ArtworkDto> artworks = artworkService.findAllArtworks();
        model.addAttribute("artworks", artworks);
        return "artwork/artworks";
    }

    @GetMapping("/new")
    public String getNewArtwork(Model model, @RequestParam String returnUrl){

        List<ArtistPartial> artistPartials = artistService.allArtistPartials();
        List<CollectionPartial> collectionPartials = collectionService.allCollectionsPartial();
        ArtworkForm artworkForm = new ArtworkForm(artistPartials, collectionPartials);

        artworkForm.setReturnUrl(returnUrl);

        boolean disableSelect = false;
        Long id = null;
        if(returnUrl.matches("/artist/[0-9]+")){
            disableSelect = true;
            String comp[] = returnUrl.split("/");
            id = Long.valueOf(comp[2]);
            artworkForm.setArtistId(id);
        }


        model.addAttribute("artwork", artworkForm);
        model.addAttribute("returnUrl", returnUrl);
        model.addAttribute("disableSelect", disableSelect);

        return "artwork/new";
    }

    @PostMapping("/new")
    public String createArtwork(@ModelAttribute("artwork") ArtworkDto artworkDto, @RequestParam("returnUrl") String returnUrl){
        Long id = artworkService.createArtwork(artworkDto);


        return "redirect:" + returnUrl;
    }

    @GetMapping("/edit/{id}")
    public String getEditArtwork(Model model, @PathVariable Long id, @RequestParam String returnUrl){
        ArtworkDto artwork = artworkService.findById(id);
        List<ArtistPartial> artistPartials = artistService.allArtistPartials();
        List<CollectionPartial> collectionPartials = collectionService.allCollectionsPartial();

        boolean disableSelect = false;

        if(returnUrl.matches("/artist/[0-9]+")){
            disableSelect = true;
        }

        ArtworkForm artworkForm = new ArtworkForm(
                artwork.getId(),
                artwork.getName(),
                artwork.getDescription(),
                artwork.getUrl(),
                artwork.getArtistId(),
                artwork.getCollectionId(),
                artistPartials,
                collectionPartials,
                returnUrl
        );


        model.addAttribute("artwork", artworkForm);
        model.addAttribute("returnUrl", returnUrl);
        model.addAttribute("disableSelect", disableSelect);

        return "artwork/edit";
    }

    @PostMapping("/edit")
    public String editArtwork(@ModelAttribute("artwork") ArtworkDto artworkDto, @RequestParam("returnUrl") String returnUrl){
        Long id = artworkService.saveArtwork(artworkDto);
        return "redirect:" + returnUrl;
    }

    @GetMapping("/delete/{id}")
    public String deleteArtist(@PathVariable Long id, @RequestParam String returnUrl){
        artworkService.deleteArtwork(id);
        return "redirect:" + returnUrl;
    }

}
