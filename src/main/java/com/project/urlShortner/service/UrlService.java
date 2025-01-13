package com.project.urlShortner.service;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.project.urlShortner.dto.QrCodeRequest;
import com.project.urlShortner.dto.UrlRequestDto;
import com.project.urlShortner.dto.UrlResponseDto;
import com.project.urlShortner.model.Url;
import com.project.urlShortner.reporsitory.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.project.urlShortner.utils.UrlShortenerUtils.randomCode;
import static com.project.urlShortner.utils.UrlShortenerUtils.toBase62;

@Service
public class UrlService {


    private static final String BASE_URL = "http://localhost:8090/";
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }


    public UrlResponseDto shortenUrl(UrlRequestDto urlRequestDto) {
        String customAlias = urlRequestDto.getCustomAlias();
        Integer expireAfterDays = urlRequestDto.getExpireAfterDays();
        Url url = new Url();
        url.setOriginalUrl(urlRequestDto.getOriginalUrl());
        url.setCreatedOn(LocalDateTime.now());

        if (expireAfterDays != null) {
            url.setExpiresOn(LocalDateTime.now().plusDays(expireAfterDays));
        }

        if (StringUtils.hasLength(customAlias)) {
            if (urlRepository.findByCustomAlias(customAlias).isPresent()) {
                throw new RuntimeException("Custom alias is already in use, please choose a different one");
            }
            url.setCustomAlias(customAlias);
            url.setShortUrl(customAlias);
            urlRepository.save(url);
            return new UrlResponseDto(urlRequestDto.getOriginalUrl(), BASE_URL + customAlias);
        }

        List<Url> existingUrlList = urlRepository.findByOriginalUrlAndIsActiveTrueAndCustomAliasIsNull(urlRequestDto.getOriginalUrl());

        if (!existingUrlList.isEmpty()) {
            for (Url existing : existingUrlList) {
                // If expireAfterDays is null and the existing URL has no expiration date
                if (expireAfterDays == null && existing.getExpiresOn() == null) {
                    // Return the first matching URL that meets the condition
                    return new UrlResponseDto(existing.getOriginalUrl(), BASE_URL + existing.getShortUrl());
                }
                if (expireAfterDays != null && existing.getExpiresOn() != null) {
                    long daysDifference = java.time.Duration.between(LocalDateTime.now(), existing.getExpiresOn()).toDays();
                    if (expireAfterDays == daysDifference) {
                        return new UrlResponseDto(existing.getOriginalUrl(), BASE_URL + existing.getShortUrl());
                    }
                }
            }
        }


        urlRepository.save(url); // Save to generate ID

        String shortUrl = toBase62(url.getId()) + randomCode();
        url.setShortUrl(shortUrl);
        urlRepository.save(url);

        return new UrlResponseDto(urlRequestDto.getOriginalUrl(), BASE_URL + shortUrl);
    }


    public Optional<Url> findByShortUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }


    public void incrementClickCountAndUpdateLastAccessed(Url url) {
        url.setClickCount(url.getClickCount() + 1);
        url.setLastAccessed(LocalDateTime.now());
        urlRepository.save(url);
    }

    public BufferedImage overlayLogo(BufferedImage qrImage, String logoUrl, int logoSizePercent, int padding, String backgroundShape, String backgroundColor) throws IOException {
        if (qrImage == null || logoUrl == null || logoUrl.isEmpty()) {
            return qrImage;
        }

        // Load the logo image
        BufferedImage logo = ImageIO.read(new URL(logoUrl));
        if (logo == null) {
            return qrImage;
        }

        // Calculate the size and position of the logo
        int qrWidth = qrImage.getWidth();
        int qrHeight = qrImage.getHeight();
        int logoSize = qrWidth * logoSizePercent / 100;
        int x = (qrWidth - logoSize) / 2;
        int y = (qrHeight - logoSize) / 2;

        // Create graphics object with antialiasing and interpolation
        Graphics2D g = qrImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Check if a valid background color is specified
        if (backgroundShape != null && !backgroundShape.isEmpty() &&
                backgroundColor != null && !backgroundColor.isEmpty()) {

            // Parse the color or skip drawing for transparency
            Color bgColor = parseColor(backgroundColor, null);
            if (bgColor != null) {
                g.setColor(bgColor);

                int backgroundSize = logoSize + (2 * padding);
                int backgroundX = x - padding;
                int backgroundY = y - padding;

                switch (backgroundShape.toLowerCase()) {
                    case "square":
                        g.fillRect(backgroundX, backgroundY, backgroundSize, backgroundSize);
                        break;

                    case "circle":
                        g.fillOval(backgroundX, backgroundY, backgroundSize, backgroundSize);
                        break;

                    case "rounded_square":
                        int arcSize = padding * 2; // Define corner roundness
                        g.fillRoundRect(backgroundX, backgroundY, backgroundSize, backgroundSize, arcSize, arcSize);
                        break;

                    default:
                        throw new IllegalArgumentException("Unsupported shape: " + backgroundShape);
                }
            }
        }

        // Draw the logo on the QR code
        g.drawImage(logo, x, y, logoSize, logoSize, null);
        g.dispose();

        return qrImage;
    }

    /**
     * Parses a color string (hex or named) into a Color object. Defaults to a given fallback color if invalid.
     *
     * @param colorStr The color string (e.g., "#FFFFFF" or "white").
     * @param fallback The fallback color.
     * @return The parsed Color object or the fallback color.
     */
    private Color parseColor(String colorStr, Color fallback) {
        try {
            return (colorStr != null && !colorStr.isEmpty()) ? Color.decode(colorStr) : fallback;
        } catch (NumberFormatException e) {
            return fallback;
        }
    }


    public byte[] generateQrCodeWithStyling(QrCodeRequest qrCodeRequest) throws WriterException, IOException {
        // Extract parameters from the request
        String url = qrCodeRequest.getUrl();
        String foregroundColor = qrCodeRequest.getForegroundColor();
        String backgroundColor = qrCodeRequest.getBackgroundColor();
        Integer width = qrCodeRequest.getWidth();
        Integer height = qrCodeRequest.getHeight();
        String logoUrl = qrCodeRequest.getLogoUrl();
        int logoSizePercent = qrCodeRequest.getLogoSizePercent();
        int logoPadding = qrCodeRequest.getLogoPadding();
        String logoBackGroundShape = qrCodeRequest.getLogoBackGroundShape();
        String logoBackGroundColour = qrCodeRequest.getLogoBackGroundColour();


        // Set default width and height if not provided
        int qrCodeWidth = (width != null) ? width : 250;  // Default to 250 if not provided
        int qrCodeHeight = (height != null) ? height : qrCodeWidth;  // Default to the same value as width if height is not provided

        // Create QR code writer
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        // Set error correction level to H
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        // Generate QR code
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, qrCodeWidth, qrCodeHeight, hints);

        // Create BufferedImage for QR code
        BufferedImage qrImage = new BufferedImage(qrCodeWidth, qrCodeHeight, BufferedImage.TYPE_INT_RGB);

        // Set background color and foreground color
        int bgColor = (backgroundColor != null && !backgroundColor.isEmpty()) ? Color.decode(backgroundColor).getRGB() : Color.WHITE.getRGB();
        int fgColor = (foregroundColor != null && !foregroundColor.isEmpty()) ? Color.decode(foregroundColor).getRGB() : Color.BLACK.getRGB();

        for (int x = 0; x < qrCodeWidth; x++) {
            for (int y = 0; y < qrCodeHeight; y++) {
                qrImage.setRGB(x, y, bitMatrix.get(x, y) ? fgColor : bgColor);
            }
        }

        qrImage = overlayLogo(qrImage, logoUrl, logoSizePercent, logoPadding, logoBackGroundShape, logoBackGroundColour);
        // Convert the BufferedImage to a byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "PNG", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    /**
     * Get analytics for a specific URL.
     */
    public Map<String, Object> getAnalytics(Url url) {
        Map<String, Object> analyticsData = new HashMap<>();
        analyticsData.put("originalUrl", url.getOriginalUrl());
        analyticsData.put("shortUrl", BASE_URL+url.getShortUrl());
        analyticsData.put("customAlias", url.getCustomAlias());
        analyticsData.put("clickCount", url.getClickCount());
        analyticsData.put("lastAccessed", url.getLastAccessed());
        analyticsData.put("createdOn", url.getCreatedOn());
        analyticsData.put("expiresOn", url.getExpiresOn());
        return analyticsData;
    }

   
}
