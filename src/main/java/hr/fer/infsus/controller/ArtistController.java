package hr.fer.infsus.controller;

import hr.fer.infsus.dto.ArtistDto;
import hr.fer.infsus.model.Artist;
import hr.fer.infsus.service.sif.ArtistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/artist")
@RequiredArgsConstructor
@Slf4j
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public String allArtists(Model model){
        List<ArtistDto> artists = artistService.findAllArtists();
        model.addAttribute("artists", artists);
        return "artist/artists";
    }

    @GetMapping("/new")
    public String getNewArtist(Model model){
        model.addAttribute("artist", new ArtistDto());
        return "artist/new";
    }

    @PostMapping("/new")
    public String createArtist(@ModelAttribute("artist") ArtistDto artist){
        Long id = artistService.saveArtist(artist);
        return "redirect:/artist";
    }
}
