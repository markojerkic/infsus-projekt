package hr.fer.infsus.controller;

import java.util.List;

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
import hr.fer.infsus.exception.ValidationException;
import hr.fer.infsus.forms.SearchQuery;
import hr.fer.infsus.service.ArtistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/artist")
@RequiredArgsConstructor
@Slf4j
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/new")
    public String getNewArtist(Model model) {
        model.addAttribute("artist", new ArtistDto());
        return "artist/new";
    }

    @GetMapping("/{id}")
    public String getArtistDetail(Model model, @PathVariable Long id,
            @RequestParam(name = "query", required = false) String query) {
        ArtistDto artist = artistService.findArtistById(id, query);

        if (query == null) {
            query = "";

        }
        model.addAttribute("artist", artist);
        model.addAttribute("search", new SearchQuery(query));
        return "artist/detail";
    }

    @GetMapping
    public String allArtists(Model model, @RequestParam(name = "query", required = false) String query) {

        List<ArtistDto> artists;

        if (query == null) {
            artists = artistService.findAllArtists();
            query = "";

        } else {
            artists = artistService.findByUsername(query);
        }
        model.addAttribute("artists", artists);
        model.addAttribute("search", new SearchQuery(query));
        return "artist/artists";
    }

    @GetMapping("/edit/{id}")
    public String getEditArtist(Model model, @PathVariable Long id) {
        ArtistDto artist = artistService.findById(id);
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

    @PostMapping("/edit")
    public String editArtist(@Valid @ModelAttribute("artist") ArtistDto artist, BindingResult bindingResult,
            @RequestParam(value = "returnUrl", defaultValue = "/artist") String returnUrl) {
        if (bindingResult.hasErrors())
            return "artist/edit";
        artistService.saveArtist(artist);
        return "redirect:" + returnUrl;
    }

    @DeleteMapping("/{id}")
    public String deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return "redirect:/artist";
    }

}
