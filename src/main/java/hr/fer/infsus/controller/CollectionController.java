package hr.fer.infsus.controller;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hr.fer.infsus.model.Collection;
import hr.fer.infsus.service.CollectionService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

    @GetMapping("/collection/new")
    public String getNewCollection(Model model) {
        model.addAttribute("collection", new Collection());
        return "collection/new";
    }

    @GetMapping("/collection")
    public String allCollections(Model model, Pageable pageable, Optional<String> name) {
        var collections = this.collectionService.getAllCollections(name, pageable);
        model.addAttribute("collections", collections);
        return "collection/list";
    }

    @PostMapping("/collection/new")
    public String saveCollection(Model model, Collection collection) {
        this.collectionService.saveCollection(collection);
        return "redirect:/collection";
    }

}
