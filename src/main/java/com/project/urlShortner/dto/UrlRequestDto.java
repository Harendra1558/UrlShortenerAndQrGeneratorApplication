package com.project.urlShortner.dto;


import com.project.urlShortner.annotation.ValidUrl;
import jakarta.validation.constraints.Pattern;

public class UrlRequestDto {


    @ValidUrl
    private String originalUrl;

    @Pattern(regexp = "^[a-zA-Z0-9_-]{1,50}$", message = "Custom alias must be alphanumeric and 1-50 characters long.")
    private String customAlias; // Optional custom alias for the shortened URL


    private Integer expireAfterDays;


    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getCustomAlias() {
        return customAlias;
    }

    public void setCustomAlias(String customAlias) {
        this.customAlias = customAlias;
    }

    public Integer getExpireAfterDays() {
        return expireAfterDays;
    }

    public void setExpireAfterDays(Integer expireAfterDays) {
        this.expireAfterDays = expireAfterDays;
    }
}
