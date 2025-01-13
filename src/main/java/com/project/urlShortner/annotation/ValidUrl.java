package com.project.urlShortner.annotation;


import com.project.urlShortner.utils.UrlValidatorConstraint;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Custom annotation for URL validation
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UrlValidatorConstraint.class)
public @interface ValidUrl {
    String message() default "Invalid or malicious URL"; // Default error message

    Class<?>[] groups() default {}; // Default groups

    Class<? extends Payload>[] payload() default {};
}
