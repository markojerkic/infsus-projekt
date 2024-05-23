package hr.fer.infsus.controller;

import hr.fer.infsus.dto.ArtistDto;
import hr.fer.infsus.service.ArtistService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        Long id = artistService.createArtist(artist);

        return "redirect:/artist";
    }

    @GetMapping("/edit/{id}")
    public String getEditArtist(Model model, @PathVariable Long id){
        ArtistDto artist = artistService.findById(id);
        model.addAttribute("artist", artist);
        return "artist/edit";
    }

    @PostMapping("/edit")
    public String editArtist(@ModelAttribute("artist") ArtistDto artist, @RequestParam("returnUrl") String returnUrl){
        artistService.saveArtist(artist);
        return "redirect:" + returnUrl;
    }

    @GetMapping("/delete/{id}")
    public String deleteArtist(@PathVariable Long id){
        artistService.deleteArtist(id);
        return "redirect:/artist";
    }

    @GetMapping("/{id}")
    public String getArtistDetail(Model model, @PathVariable Long id){
        ArtistDto artist = artistService.findArtistById(id);
        model.addAttribute("artist", artist);
        return "artist/detail";
    }
}
