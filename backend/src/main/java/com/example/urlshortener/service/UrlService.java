package com.example.urlshortener.service;

import com.example.urlshortener.model.UrlMapping;
import com.example.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlService {

    @Autowired
    private UrlRepository repository;

    public UrlMapping createShortUrl(String fullUrl, Optional<String> customAlias) {
        String alias = customAlias.orElse(UUID.randomUUID().toString().substring(0, 6));

        if (repository.existsById(alias))
            throw new IllegalArgumentException("Alias already exists");

        UrlMapping mapping = new UrlMapping(alias, fullUrl);
        return repository.save(mapping);
    }

    public UrlMapping getByAlias(String alias) {
        return repository.findById(alias).orElseThrow(() -> new IllegalArgumentException("Alias not found"));
    }

    public void deleteByAlias(String alias) {
        if (!repository.existsById(alias))
            throw new IllegalArgumentException("Alias not found");

        repository.deleteById(alias);
    }

    public List<UrlMapping> getAll() {
        return repository.findAll();
    }
}
