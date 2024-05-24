package hr.fer.infsus.controller;

import hr.fer.infsus.dto.ArtistDto;
import hr.fer.infsus.forms.SearchQuery;
import hr.fer.infsus.service.ArtistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.SearchControls;
import java.util.List;

@Controller
@RequestMapping("/artist")
@RequiredArgsConstructor
@Slf4j
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public String allArtists(Model model, @RequestParam(name = "query", required = false)String query){


        List<ArtistDto> artists;


        if(query == null){
            artists  = artistService.findAllArtists();
            query = "";

        } else {
            artists = artistService.findByUsername(query);
        }
        model.addAttribute("artists", artists);
        model.addAttribute("search", new SearchQuery(query));
        return "artist/artists";
    }

    @GetMapping("/new")
    public String getNewArtist(Model model){
        model.addAttribute("artist", new ArtistDto());
        return "artist/new";
    }

    @PostMapping("/new")
    public String createArtist(@Valid  @ModelAttribute("artist") ArtistDto artist, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "artist/new";
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
    public String editArtist(@Valid @ModelAttribute("artist") ArtistDto artist, BindingResult bindingResult, @RequestParam(value = "returnUrl", defaultValue = "/artist") String returnUrl){
        if(bindingResult.hasErrors()) return "artist/edit";
        artistService.saveArtist(artist);
        return "redirect:" + returnUrl;
    }

    @GetMapping("/delete/{id}")
    public String deleteArtist(@PathVariable Long id){
        artistService.deleteArtist(id);
        return "redirect:/artist";
    }

    @GetMapping("/{id}")
    public String getArtistDetail(Model model, @PathVariable Long id, @RequestParam(name = "query", required = false)String query){
        ArtistDto artist = artistService.findArtistById(id, query);

        if(query == null){
            query = "";

        }
        model.addAttribute("artist", artist);
        model.addAttribute("search", new SearchQuery(query));
        return "artist/detail";
    }
}
