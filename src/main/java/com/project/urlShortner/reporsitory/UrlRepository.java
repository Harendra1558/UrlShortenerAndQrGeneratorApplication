package com.project.urlShortner.reporsitory;


import com.project.urlShortner.model.Url;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Integer> {


    // Find URL by its short URL
    Optional<Url> findByShortUrl(String shortUrl);

    // Find URL by its original URL
    Optional<Url> findByOriginalUrl(String originalUrl);

    // Find URL by custom alias
    Optional<Url> findByCustomAlias(String customAlias);

    // Find all URLs created by a specific date
    List<Url> findByCreatedOnBefore(LocalDateTime date);

    // Find all URLs that expire after a specific date
    List<Url> findByExpiresOnAfter(LocalDateTime date);

    // Find all URLs that are active
    List<Url> findByIsActiveTrue();

    // Find all URLs that are inactive
    List<Url> findByIsActiveFalse();

    // Find all URLs that have been accessed more than a certain number of times
    List<Url> findByClickCountGreaterThan(int clickCount);

    // Find all URLs that were last accessed before a certain date
    List<Url> findByLastAccessedBefore(LocalDateTime date);

    // Find all URLs by a specific short URL or custom alias
    Optional<Url> findByShortUrlOrCustomAlias(String shortUrl, String customAlias);

    // Delete all expired URLs
    @Transactional
    void deleteByExpiresOnBefore(LocalDateTime date);

    List<Url> findByOriginalUrlAndIsActiveTrue(String originalUrl);


    Optional<Url> findByOriginalUrlAndIsActiveTrueAndExpiresOn(String originalUrl, LocalDateTime expiresOn);

    List<Url> findByOriginalUrlAndIsActiveTrueAndCustomAliasIsNull(String originalUrl);


}
