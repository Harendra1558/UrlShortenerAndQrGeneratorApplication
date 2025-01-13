package com.project.urlShortner.utils;

import java.util.Stack;
import java.util.UUID;

public class UrlShortenerUtils {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private UrlShortenerUtils() {

    }

    // Convert a number to a Base62 string.
    public static String toBase62(long number) {
        final int BASE62 = ALPHABET.length();
        final StringBuilder sb = new StringBuilder();

        do {
            sb.insert(0, ALPHABET.charAt((int) (number % BASE62)));
            number /= BASE62;
        } while (number > 0);

        return sb.toString();
    }

    // Generate a random code based on UUID (last 4 characters for better uniqueness)
    public static String randomCode() {
        UUID uuid = UUID.randomUUID();
        long lo = uuid.getLeastSignificantBits();
        long hi = uuid.getMostSignificantBits();
        lo = (lo >> (64 - 31)) ^ lo;
        hi = (hi >> (64 - 31)) ^ hi;
        String s = String.format("%010d", Math.abs(hi) + Math.abs(lo));
        return s.substring(s.length() - 4);
    }


    public static long fromBase62(String base62String) {
        final int BASE62 = ALPHABET.length();
        long result = 0;

        for (int i = 0; i < base62String.length(); i++) {
            char c = base62String.charAt(i);
            int value = ALPHABET.indexOf(c);
            if (value == -1) {
                throw new IllegalArgumentException("Invalid character in Base62 string: " + c);
            }
            result = result * BASE62 + value;
        }

        return result;
    }





}
