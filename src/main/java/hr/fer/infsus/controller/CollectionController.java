package hr.fer.infsus.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.fer.infsus.dto.query.CollectionQueryDto;
import hr.fer.infsus.model.Collection;
import hr.fer.infsus.service.CollectionService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/collection")
public class CollectionController {

    private final CollectionService collectionService;

    @GetMapping("/new")
    public String getNewCollection(Model model) {
        model.addAttribute("collection", new Collection());
        return "collection/new";
    }

    @GetMapping("/edit/{id}")
    public String getEditCollection(Model model, @PathVariable Long id) {
        var collection = this.collectionService.getCollectionById(id);
        model.addAttribute("id", id);
        model.addAttribute("collection", collection);
        return "collection/edit";
    }

    @GetMapping
    public String allCollections(Model model, Pageable pageable, CollectionQueryDto query) {
        var collections = this.collectionService.getAllCollections(query, pageable);
        model.addAttribute("collections", collections);
        model.addAttribute("search", query);
        model.addAttribute("pageable", pageable);
        return "collection/list";
    }

    @PostMapping("/new")
    public String saveCollection(Model model, Collection collection) {
        this.collectionService.saveCollection(collection);
        return "redirect:/collection";
    }

    @PostMapping("/edit/{id}")
    public String updateCollection(Model model, @PathVariable Long id, Collection collection) {
        this.collectionService.updateCollection(id, collection);
        return "redirect:/collection";

    }

    @DeleteMapping("/{id}")
    public String deleteCollection(@PathVariable Long id, Model model) {
        this.collectionService.deleteCollection(id);

        model.addAttribute("collections", Page.empty());

        return "collection/list :: search-items";
    }

}
