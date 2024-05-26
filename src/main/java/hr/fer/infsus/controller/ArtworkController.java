package hr.fer.infsus.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hr.fer.infsus.dto.query.CollectionQueryDto;
import hr.fer.infsus.exception.ValidationException;
import hr.fer.infsus.forms.ArtworkForm;
import hr.fer.infsus.forms.VideoForm;
import hr.fer.infsus.service.ArtworkService;
import hr.fer.infsus.service.CollectionService;
import hr.fer.infsus.service.sif.GenreService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/artwork")
@RequiredArgsConstructor
@Slf4j
public class ArtworkController {
    private final ArtworkService artworkService;
    private final CollectionService collectionService;
    private final GenreService genreService;

    @GetMapping("/{artistId}/new")
    public String getNewArtwork(Model model, @PathVariable Long artistId) {

        var collections = this.collectionService.getAllCollections(new CollectionQueryDto(), PageRequest.of(0, 25));
        var genres = this.genreService.getGenres(PageRequest.of(0, 25), Optional.empty(), Optional.empty());

        var videoForm = new VideoForm();
        var artworkForm = new ArtworkForm();
        artworkForm.setVideo(videoForm);

        artworkForm.setArtistId(artistId);
        model.addAttribute("artwork", artworkForm);
        model.addAttribute("collections", collections);
        model.addAttribute("artistId", artistId);
        model.addAttribute("genres", genres);

        return "artwork/new";
    }

    @GetMapping("/{artistId}/edit/{id}")
    public String getEditArtwork(Model model, @PathVariable Long id, @PathVariable Long artistId) {

        var collections = this.collectionService.getAllCollections(new CollectionQueryDto(), PageRequest.of(0, 25));
        var genres = this.genreService.getGenres(PageRequest.of(0, 25), Optional.empty(), Optional.empty());

        var artwork = this.artworkService.getById(id);
        var artworkForm = new ArtworkForm(artwork);

        artworkForm.setArtistId(artistId);
        model.addAttribute("id", id);
        model.addAttribute("artwork", artworkForm);
        model.addAttribute("collections", collections);
        model.addAttribute("artistId", artistId);
        model.addAttribute("genres", genres);

        return "artwork/edit";
    }

    @PostMapping("/{artistId}/new")
    public String createArtwork(Model model,
            @PathVariable Long artistId,
            @RequestParam(name = "cancel", defaultValue = "false") boolean cancel,
            @Valid @ModelAttribute("artwork") ArtworkForm artworkForm,
            HttpServletResponse response,
            BindingResult bindingResult) {

        if (cancel) {
            log.info("Canceling creation of artwork with artist id {}", artistId);
            return String.format("redirect:/artist/%d", artistId);
        }

        if (bindingResult.hasErrors()) {
            log.error("Invalid artwork form: {}", bindingResult);
            var collections = this.collectionService.getAllCollections(new CollectionQueryDto(), PageRequest.of(0, 25));
            var genres = this.genreService.getGenres(PageRequest.of(0, 25), Optional.empty(), Optional.empty());

            artworkForm.setArtistId(artistId);

            model.addAttribute("artwork", artworkForm);
            model.addAttribute("collections", collections);
            model.addAttribute("artistId", artistId);
            model.addAttribute("genres", genres);

            response.addHeader("HX-Reselect", "#artwork-form");
            response.addHeader("HX-Retarget", "closest div");
            response.addHeader("HX-Push-Url", "/artist/" + artistId);

            return "artwork/new";
        }

        try {
            this.artworkService.createArtwork(artworkForm);
        } catch (ValidationException e) {
            log.error("Validation exception: {}", e);
            var collections = this.collectionService.getAllCollections(new CollectionQueryDto(), PageRequest.of(0, 25));
            var genres = this.genreService.getGenres(PageRequest.of(0, 25), Optional.empty(), Optional.empty());

            artworkForm.setArtistId(artistId);

            model.addAttribute("artwork", artworkForm);
            model.addAttribute("collections", collections);
            model.addAttribute("artistId", artistId);
            model.addAttribute("genres", genres);

            bindingResult.addError(new FieldError("artwork", e.getFieldName(), e.getMessage()));

            response.addHeader("HX-Reselect", "#artwork-form");
            response.addHeader("HX-Retarget", "closest div");
            response.addHeader("HX-Push-Url", "/artist/" + artistId);

            return "artwork/new";
        }

        return String.format("redirect:/artist/%d", artistId);
    }

    @PostMapping("/{artistId}/edit/{id}")
    public String updateArtwork(Model model,
            @PathVariable Long id,
            @PathVariable Long artistId,
            @RequestParam(name = "cancel", defaultValue = "false") boolean cancel,
            @Valid @ModelAttribute("artwork") ArtworkForm artworkForm,
            BindingResult bindingResult) {

        if (cancel) {
            log.info("Canceling edit of artwork with id {}", id);
            return String.format("redirect:/artist/%d", artistId);
        }

        if (bindingResult.hasErrors()) {
            var collections = this.collectionService.getAllCollections(new CollectionQueryDto(), PageRequest.of(0, 25));
            var genres = this.genreService.getGenres(PageRequest.of(0, 25), Optional.empty(), Optional.empty());

            artworkForm.setArtistId(artistId);

            model.addAttribute("id", id);
            model.addAttribute("artwork", artworkForm);
            model.addAttribute("collections", collections);
            model.addAttribute("artistId", artistId);
            model.addAttribute("genres", genres);

            return "artwork/edit";
        }

        try {
            this.artworkService.saveArtwork(id, artworkForm);
        } catch (ValidationException e) {
            var collections = this.collectionService.getAllCollections(new CollectionQueryDto(), PageRequest.of(0, 25));
            var genres = this.genreService.getGenres(PageRequest.of(0, 25), Optional.empty(), Optional.empty());

            artworkForm.setArtistId(artistId);

            model.addAttribute("id", id);
            model.addAttribute("artwork", artworkForm);
            model.addAttribute("collections", collections);
            model.addAttribute("artistId", artistId);
            model.addAttribute("genres", genres);

            bindingResult.addError(new FieldError("artwork", e.getFieldName(), e.getMessage()));

            return "artwork/edit";
        }

        return String.format("redirect:/artist/%d", artistId);
    }

    @DeleteMapping("/{id}")
    public String deleteArtwork(@PathVariable Long id, Model model) {
        this.artworkService.deleteArtwork(id);
        model.addAttribute("artworks", Page.empty());

        return "artist/detail :: search-items";

    }

}
