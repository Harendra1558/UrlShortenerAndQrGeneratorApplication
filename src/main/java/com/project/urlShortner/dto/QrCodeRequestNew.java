package com.project.urlShortner.dto;

public class QrCodeRequestNew {

    private String data; // The data or URL to encode in the QR code
    private Config config; // QR code customization configuration
    private Integer size; // QR code size
    private boolean download; // Whether the QR code should be downloaded
    private String file; // File type (e.g., PNG, SVG)

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public boolean isDownload() {
        return download;
    }

    public void setDownload(boolean download) {
        this.download = download;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    // Nested Config class for QR customization


    // Getters and Setters
}
