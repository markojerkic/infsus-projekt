package hr.fer.infsus.controller;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.fer.infsus.model.types.sif.Genre;
import hr.fer.infsus.service.sif.GenreService;
import hr.fer.infsus.util.HtmxRequestUtil;
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
    public String index(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Optional<String> name,
            Optional<String> description,
            ModelMap model,
            HttpServletRequest request) {

        var results = this.genreService.getGenres(pageable, name, description);

        model.addAttribute("genresPage", results);

        name.ifPresent(n -> model.addAttribute("searchName", n));
        description.ifPresent(d -> model.addAttribute("searchDescription", d));

        if (HtmxRequestUtil.isHtmxRequest(request)) {
            return "genre/list :: search-table";
        }

        return "genre/list";
    }

    @GetMapping("create")
    public String create(ModelMap model, HttpServletRequest httpServletRequest) {
        model.addAttribute("currentPage", new Genre());
        model.addAttribute("isEdit", false);
        return "genre/create";
    }

    @GetMapping("/{id}")
    public String genreById(@PathVariable Long id, ModelMap model, HttpServletRequest httpServletRequest) {

        var genre = this.genreService.getGenreById(id);

        model.addAttribute("genre", genre);
        return "genre/genre";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, ModelMap model, HttpServletRequest httpServletRequest) {
        var genre = this.genreService.getGenreById(id);

        model.addAttribute("genre", genre);
        model.addAttribute("isEdit", true);
        return "genre/create";
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

    @PostMapping("/{id}")
    public String updateGenre(@PathVariable Long id,
            @Valid @ModelAttribute("employee") Genre genre,
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

        response.addHeader("HX-Redirect", "/genre/" + createdGenre.getId());

        model.addAttribute("genre", createdGenre);

        return "genre/genre";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {

        this.genreService.deleteGenre(id);

        if (HtmxRequestUtil.isHtmxRequest(request)) {
            log.info("HX-Request");
            return "fragments/empty";
        }

        return "redirect:/genre";
    }

}
