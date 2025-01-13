package com.project.urlShortner.dto;

public class UrlResponseDto {

    private String originalUrl;
    private String shortUrl;

    // Constructor
    public UrlResponseDto(String originalUrl, String shortUrl) {
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
    }

    // Getters and Setters
    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
