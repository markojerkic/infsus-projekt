package hr.fer.infsus.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import hr.fer.infsus.dto.artist.ArtistDto;
import hr.fer.infsus.dto.artist.NewArtistDto;
import hr.fer.infsus.dto.query.ArtistQueryDto;
import hr.fer.infsus.dto.query.ArtworkQueryDto;
import hr.fer.infsus.exception.ValidationException;
import hr.fer.infsus.model.Artist;
import hr.fer.infsus.service.ArtistService;
import hr.fer.infsus.service.ArtworkService;
import hr.fer.infsus.util.HtmxRequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    private final ArtworkService artworkService;

    @GetMapping("/new")
    public String getNewArtist(Model model) {
        model.addAttribute("artist", new NewArtistDto());
        return "artist/new";
    }

    @GetMapping("/{id}")
    public String getArtistDetail(Model model, @PathVariable Long id,
            Pageable pageable,
            ArtworkQueryDto query) {
        var artist = this.artistService.getArtistById(id);
        var artworks = this.artworkService.findAllArtworks(id, pageable, query);

        model.addAttribute("artist", artist);
        model.addAttribute("artworks", artworks);
        return "artist/detail";
    }

    @GetMapping
    public String allArtists(Model model, Pageable pageable, @ModelAttribute ArtistQueryDto query) {

        var artists = this.artistService.findAllArtists(pageable, query);

        model.addAttribute("artists", artists);
        model.addAttribute("search", query);
        model.addAttribute("pageable", pageable);
        return "artist/artists";
    }

    @GetMapping("/edit/{id}")
    public String getEditArtist(Model model, @PathVariable Long id) {
        var artist = this.artistService.getArtistById(id);
        model.addAttribute("artist", artist);
        return "artist/edit";
    }

    @PostMapping
    public String createArtist(@Valid @ModelAttribute("artist") NewArtistDto artist, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "artist/new";
        }
        try {
            Long id = artistService.createArtist(artist);
            return "redirect:/artist";
        } catch (ValidationException e) {
            bindingResult.addError(new FieldError("artist", e.getFieldName(), e.getMessage()));
            return "artist/new";
        }

    }

    @PostMapping("/edit/{id}")
    public String editArtist(@PathVariable Long id, @Valid @ModelAttribute("artist") NewArtistDto artist,
            BindingResult bindingResult,
            Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "artist/edit";
        }
        var savedArtist = this.artistService.saveArtist(id, artist);
        if (HtmxRequestUtil.isHtmxRequest(request)) {
            model.addAttribute("artist", savedArtist);
            return "artist/detail :: artist-form";
        }

        return "redirect:/artist";
    }

    @DeleteMapping("/{id}")
    public String deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return "redirect:/artist";
    }

}
