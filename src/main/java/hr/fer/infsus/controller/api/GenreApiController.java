package hr.fer.infsus.controller.api;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hr.fer.infsus.model.types.sif.Genre;
import hr.fer.infsus.service.GenreService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/genre")
@RequiredArgsConstructor
public class GenreApiController {
    private final GenreService genreService;

    @GetMapping
    public Page<Genre> getGenres(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Optional<String> name,
            Optional<String> description) {
        return this.genreService.getGenres(pageable, name, description);
    }

}
