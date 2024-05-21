package hr.fer.infsus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import hr.fer.infsus.model.types.sif.Genre;
import hr.fer.infsus.service.sif.GenreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/genre")
@RequiredArgsConstructor
@Slf4j
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public String index(ModelMap model, HttpServletRequest httpServletRequest) {
        model.addAttribute("currentPage", "genre");
        model.addAttribute("httpServletRequest", httpServletRequest);
        return "genre/create";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, ModelMap model, HttpServletRequest httpServletRequest) {
        var genre = this.genreService.getGenreById(id);

        model.addAttribute("genre", genre);
        return "genre/create";
    }

    @GetMapping("/{id}")
    public String genreById(@PathVariable Long id, ModelMap model, HttpServletRequest httpServletRequest) {

        var genre = this.genreService.getGenreById(id);

        model.addAttribute("genre", genre);
        return "genre/genre";
    }

    @PostMapping
    public String createGenre(@Valid @ModelAttribute("employee") Genre genre,
            BindingResult bindingResult,
            HttpServletResponse response,
            ModelMap model) {

        if (bindingResult.hasErrors()) {
            var error = bindingResult.getFieldError();
            var target = error.getField();
            var message = error.getDefaultMessage();

            response.addHeader("HX-Retarget", "small[data-error='" + target + "']");
            response.addHeader("HX-Reswap", "outerHTML");

            log.error("Validation error: {} - {}", target, message);

            model.addAttribute("message", message);
            model.addAttribute("target", target);
            return "errors/validation-error";
        }

        var createdGenre = this.genreService.createGenre(genre);

        // response.addHeader("HX-Reselect", "body");
        // response.addHeader("HX-Retarget", "body");
        // response.addHeader("HX-Reswap", "outerHTML");
        response.addHeader("HX-Redirect", "/genre/" + createdGenre.getId());

        model.addAttribute("genre", createdGenre);

        return "genre/genre";
    }

}
