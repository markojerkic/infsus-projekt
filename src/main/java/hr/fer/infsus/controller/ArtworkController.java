package hr.fer.infsus.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.fer.infsus.dto.query.CollectionQueryDto;
import hr.fer.infsus.forms.ArtworkForm;
import hr.fer.infsus.model.Artwork;
import hr.fer.infsus.service.ArtworkService;
import hr.fer.infsus.service.CollectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/artwork")
@RequiredArgsConstructor
public class ArtworkController {
    private final ArtworkService artworkService;
    private final CollectionService collectionService;

    @GetMapping("/{artistId}/new")
    public String getNewArtwork(Model model, @PathVariable Long artistId) {

        var collections = this.collectionService.getAllCollections(new CollectionQueryDto(), PageRequest.of(0, 25));
        var artworkForm = new ArtworkForm();

        artworkForm.setArtistId(artistId);
        model.addAttribute("artwork", artworkForm);
        model.addAttribute("collections", collections);
        model.addAttribute("artistId", artistId);

        return "artwork/new";
    }

    @GetMapping("/{artistId}/edit/{id}")
    public String getEditArtwork(Model model, @PathVariable Long id, @PathVariable Long artistId) {

        var collections = this.collectionService.getAllCollections(new CollectionQueryDto(), PageRequest.of(0, 25));
        var artwork = this.artworkService.getById(id);
        var artworkForm = new ArtworkForm(artwork);

        artworkForm.setArtistId(artistId);
        model.addAttribute("id", id);
        model.addAttribute("artwork", artworkForm);
        model.addAttribute("collections", collections);
        model.addAttribute("artistId", artistId);

        return "artwork/edit";
    }

    @PostMapping("/{artistId}/new")
    public String createArtwork(Model model,
            @PathVariable Long artistId,
            @Valid @ModelAttribute("artwork") ArtworkForm artworkForm,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            var collections = this.collectionService.getAllCollections(new CollectionQueryDto(), PageRequest.of(0, 25));

            artworkForm.setArtistId(artistId);

            model.addAttribute("artwork", artworkForm);
            model.addAttribute("collections", collections);
            model.addAttribute("artistId", artistId);

            return "artwork/new";
        }

        Artwork artwork = this.artworkService.createArtwork(artworkForm);

        return String.format("redirect:/artist/%d", artistId);
    }

    @PostMapping("/{artistId}/edit/{id}")
    public String updateArtwork(Model model,
            @PathVariable Long id,
            @PathVariable Long artistId,
            @Valid @ModelAttribute("artwork") ArtworkForm artworkForm,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            var collections = this.collectionService.getAllCollections(new CollectionQueryDto(), PageRequest.of(0, 25));

            artworkForm.setArtistId(artistId);

            model.addAttribute("id", id);
            model.addAttribute("artwork", artworkForm);
            model.addAttribute("collections", collections);
            model.addAttribute("artistId", artistId);

            return "artwork/edit";
        }

        this.artworkService.saveArtwork(id, artworkForm);

        return String.format("redirect:/artist/%d", artistId);
    }

}
