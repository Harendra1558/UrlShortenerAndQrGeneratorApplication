package com.project.urlShortner.utils;

import com.project.urlShortner.annotation.ValidUrl;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlValidatorConstraint implements ConstraintValidator<ValidUrl, String> {

    // Regular expression for basic URL format validation
    private static final String URL_REGEX = "^(https?|ftp)://[^\s/$.?#].[^\s]*$";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX, Pattern.CASE_INSENSITIVE);

    // Method to validate URL format
    private boolean isValidUrlFormat(String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        return matcher.matches();
    }

    // Method to check for malicious content like JavaScript or script injections
    private boolean containsMaliciousContent(String url) {
        return url.toLowerCase().contains("javascript:") || url.toLowerCase().contains("script");
    }

    @Override
    public void initialize(ValidUrl constraintAnnotation) {
        // Initialization logic, if necessary (empty in this case)
    }

    // Validate method using the UrlValidator class
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Perform validation
        if (value == null || value.isEmpty()) {
            return false; // Or handle custom validation logic
        }

        // Perform format validation and check for malicious content
        return isValidUrlFormat(value) && !containsMaliciousContent(value);
    }
}
