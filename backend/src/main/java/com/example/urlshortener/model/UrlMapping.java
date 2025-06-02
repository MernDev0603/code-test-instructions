package com.example.urlshortener.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UrlMapping {
    @Id
    private String alias;
    private String fullUrl;

    public UrlMapping() {}

    public UrlMapping(String alias, String fullUrl) {
        this.alias = alias;
        this.fullUrl = fullUrl;
    }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }
    public String getFullUrl() { return fullUrl; }
    public void setFullUrl(String fullUrl) { this.fullUrl = fullUrl; }
}
