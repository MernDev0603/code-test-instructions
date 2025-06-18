package com.example.urlshortener.service;

import com.example.urlshortener.model.UrlMapping;
import com.example.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.math.BigInteger;

@Service
public class UrlService {

    @Autowired
    private UrlRepository repository;

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String generateAlias(String url) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(url.getBytes(StandardCharsets.UTF_8));
            return encodeBase62(hash).substring(0, 8); // you can adjust length
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing algorithm not available", e);
        }
    }

    private static String encodeBase62(byte[] input) {
        BigInteger number = new BigInteger(1, input); // 1 to avoid negative values
        StringBuilder sb = new StringBuilder();

        while (number.compareTo(BigInteger.ZERO) > 0) {
            int remainder = number.mod(BigInteger.valueOf(62)).intValue();
            sb.append(BASE62.charAt(remainder));
            number = number.divide(BigInteger.valueOf(62));
        }

        return sb.reverse().toString();
    }

    public UrlMapping createShortUrl(String fullUrl, Optional<String> customAlias) {
        // String alias = customAlias.orElse(UUID.randomUUID().toString().substring(0, 6));
        String alias = customAlias.orElseGet(() -> generateAlias(fullUrl + System.currentTimeMillis()));

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
