package com.example.urlshortener.controller;

import com.example.urlshortener.model.UrlMapping;
import com.example.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.*;

@RestController
public class UrlController {

    @Autowired
    private UrlService service;

    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shorten(@RequestBody Map<String, String> request) {
        String fullUrl = request.get("fullUrl");
        String customAlias = request.get("customAlias");

        Optional<String> aliasOptional = (customAlias != null && !customAlias.trim().isEmpty())
                ? Optional.of(customAlias.trim())
                : Optional.empty();

        UrlMapping mapping = service.createShortUrl(fullUrl, aliasOptional);
        return ResponseEntity.status(201).body(Map.of("shortUrl", "http://localhost:8080/" + mapping.getAlias()));
    }


    @GetMapping("/{alias}")
    public ResponseEntity<Void> redirect(@PathVariable String alias) {
        UrlMapping mapping = service.getByAlias(alias);
        return ResponseEntity.status(302).location(URI.create(mapping.getFullUrl())).build();
    }

    @DeleteMapping("/{alias}")
    public ResponseEntity<Void> delete(@PathVariable String alias) {
        service.deleteByAlias(alias);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/urls")
    public List<Map<String, String>> listAll() {
        return service.getAll().stream().map(mapping -> Map.of(
                "alias", mapping.getAlias(),
                "fullUrl", mapping.getFullUrl(),
                "shortUrl", "http://localhost:8080/" + mapping.getAlias()
        )).toList();
    }
}
