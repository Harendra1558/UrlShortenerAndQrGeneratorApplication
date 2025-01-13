package com.project.urlShortner.controller;


import com.google.zxing.WriterException;
import com.project.urlShortner.dto.ApiResponse;
import com.project.urlShortner.dto.QrCodeRequest;
import com.project.urlShortner.dto.UrlRequestDto;
import com.project.urlShortner.dto.UrlResponseDto;
import com.project.urlShortner.model.Url;
import com.project.urlShortner.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }


    // Shorten a URL
    @PostMapping("url/shorten")
    public ResponseEntity<ApiResponse<UrlResponseDto>> shortenUrl(@RequestBody @Valid UrlRequestDto urlRequestDto) {
        UrlResponseDto urlResponseDto = urlService.shortenUrl(urlRequestDto);
        ApiResponse<UrlResponseDto> response = new ApiResponse<>(
                true,
                urlResponseDto,
                "URL successfully shortened.",
                HttpStatus.CREATED.value()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortUrl) {
        Optional<Url> urlOptional = urlService.findByShortUrl(shortUrl);
        if (urlOptional.isPresent()) {
            Url url = urlOptional.get();
            urlService.incrementClickCountAndUpdateLastAccessed(url);
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                    .location(URI.create(url.getOriginalUrl()))
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping("/qr")
    public ResponseEntity<ApiResponse<byte[]>> generateQrCodeWithStyling(@RequestBody QrCodeRequest qrCodeRequest) throws IOException, WriterException {

        byte[] qrCode = urlService.generateQrCodeWithStyling(qrCodeRequest);
        // Create the response
        ApiResponse<byte[]> response = new ApiResponse<>(
                true,
                qrCode,
                "QR Code generated successfully with custom styling.",
                HttpStatus.OK.value()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/qr/image")
    public ResponseEntity<byte[]> generateQrCodeWithStyling1(@RequestBody QrCodeRequest qrCodeRequest) throws IOException, WriterException {

        // Generate QR code with the custom styling options
        byte[] qrCodeImage = urlService.generateQrCodeWithStyling(qrCodeRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        // Return the image in the response body
        return new ResponseEntity<>(qrCodeImage, headers, HttpStatus.OK);
    }


    @PostMapping("/dynamic/qr")
    public ResponseEntity<ApiResponse<byte[]>> generateDynamicQrCode(@RequestBody QrCodeRequest qrCodeRequest) throws IOException, WriterException {

        UrlRequestDto urlRequestDto = new UrlRequestDto();
        urlRequestDto.setOriginalUrl(qrCodeRequest.getUrl());
        UrlResponseDto urlResponseDto = urlService.shortenUrl(urlRequestDto);
        qrCodeRequest.setUrl(urlResponseDto.getShortUrl());
        byte[] qrCode = urlService.generateQrCodeWithStyling(qrCodeRequest);
        // Create the response
        ApiResponse<byte[]> response = new ApiResponse<>(
                true,
                qrCode,
                "QR Code generated successfully with custom styling.",
                HttpStatus.OK.value()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Get analytics for a specific short URL.
     */
    @GetMapping("/analytics/{shortUrl}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAnalytics(@PathVariable String shortUrl) {
        Optional<Url> urlOptional = urlService.findByShortUrl(shortUrl);
        if (urlOptional.isPresent()) {
            Url url = urlOptional.get();
            Map<String, Object> analyticsData = urlService.getAnalytics(url);
            ApiResponse<Map<String, Object>> response = new ApiResponse<>(
                    true,
                    analyticsData,
                    "Analytics data retrieved successfully.",
                    HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    false,
                    null,
                    "Short URL not found.",
                    HttpStatus.NOT_FOUND.value()
            ));
        }
    }


}
