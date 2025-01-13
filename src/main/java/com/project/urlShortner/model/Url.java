package com.project.urlShortner.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "urls")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "original_url", nullable = false, length = 2083)
    private String originalUrl;

    @Column(name = "short_url", unique = true, length = 64)
    private String shortUrl;

    @Column(name = "custom_alias", unique = true, length = 50)
    private String customAlias;

    @Column(name = "last_accessed", columnDefinition = "TIMESTAMP DEFAULT NULL")
    private LocalDateTime lastAccessed;

    @Column(name = "click_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int clickCount = 0;

    @Column(name = "expires_on", columnDefinition = "TIMESTAMP DEFAULT NULL")
    private LocalDateTime expiresOn;

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive = true;

    @Column(name = "created_on", nullable = false, updatable = false)
    private LocalDateTime createdOn;

    /**
     * Default no-argument constructor required by JPA.
     * <p>
     * This constructor is intentionally left empty because:
     * - JPA requires a no-argument constructor to instantiate the entity using reflection.
     * - The application logic should use parameterized constructors or setters to populate fields as needed.
     */
    public Url() {
        // Intentionally left empty for JPA
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getCustomAlias() {
        return customAlias;
    }

    public void setCustomAlias(String customAlias) {
        this.customAlias = customAlias;
    }

    public LocalDateTime getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(LocalDateTime lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public LocalDateTime getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(LocalDateTime expiresOn) {
        this.expiresOn = expiresOn;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Url url)) return false;
        return getClickCount() == url.getClickCount() && isActive() == url.isActive() && Objects.equals(getId(), url.getId()) && Objects.equals(getOriginalUrl(), url.getOriginalUrl()) && Objects.equals(getShortUrl(), url.getShortUrl()) && Objects.equals(getCustomAlias(), url.getCustomAlias()) && Objects.equals(getLastAccessed(), url.getLastAccessed()) && Objects.equals(getExpiresOn(), url.getExpiresOn()) && Objects.equals(getCreatedOn(), url.getCreatedOn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOriginalUrl(), getShortUrl(), getCustomAlias(), getLastAccessed(), getClickCount(), getExpiresOn(), isActive(), getCreatedOn());
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", originalUrl='" + originalUrl + '\'' +
                ", shortUrl='" + shortUrl + '\'' +
                ", customAlias='" + customAlias + '\'' +
                ", lastAccessed=" + lastAccessed +
                ", clickCount=" + clickCount +
                ", expiresOn=" + expiresOn +
                ", isActive=" + isActive +
                ", createdOn=" + createdOn +
                '}';
    }


}
