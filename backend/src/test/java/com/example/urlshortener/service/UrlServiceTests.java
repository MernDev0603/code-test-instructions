package com.example.urlshortener.service;

import com.example.urlshortener.model.UrlMapping;
import com.example.urlshortener.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

@SpringBootTest
public class UrlServiceTests {

    @Mock
    UrlRepository repository;

    @InjectMocks
    UrlService service;

    @Test
    public void testCreateShortUrl_WithCustomAlias() {
        String alias = "test123";
        String fullUrl = "https://google.com";

        when(repository.existsById(alias)).thenReturn(false);
        when(repository.save(any())).thenReturn(new UrlMapping(alias, fullUrl));

        UrlMapping result = service.createShortUrl(fullUrl, Optional.of(alias));
        assertEquals(alias, result.getAlias());
        assertEquals(fullUrl, result.getFullUrl());
    }

    @Test
    public void testCreateShortUrl_AliasAlreadyExists() {
        String alias = "test123";
        when(repository.existsById(alias)).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> service.createShortUrl("url", Optional.of(alias)));
    }
}