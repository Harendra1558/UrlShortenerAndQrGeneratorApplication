package com.project.urlShortner.dto;

import jakarta.validation.constraints.NotBlank;

public class QrCodeRequest {


    @NotBlank(message = "Url can not be null or blank")
    private String url;
    private String foregroundColor;
    private int logoSizePercent = 20;
    private int logoPadding = 0;
    private String logoBackGroundShape;
    private String logoBackGroundColour;
    private String backgroundColor;
    private Integer width;
    private Integer height;
    private String logoUrl;

    // Getters and setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(String foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public int getLogoSizePercent() {
        return logoSizePercent;
    }

    public void setLogoSizePercent(int logoSizePercent) {
        this.logoSizePercent = logoSizePercent;
    }

    public int getLogoPadding() {
        return logoPadding;
    }

    public void setLogoPadding(int logoPadding) {
        this.logoPadding = logoPadding;
    }

    public String getLogoBackGroundShape() {
        return logoBackGroundShape;
    }

    public void setLogoBackGroundShape(String logoBackGroundShape) {
        this.logoBackGroundShape = logoBackGroundShape;
    }

    public String getLogoBackGroundColour() {
        return logoBackGroundColour;
    }

    public void setLogoBackGroundColour(String logoBackGroundColour) {
        this.logoBackGroundColour = logoBackGroundColour;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}