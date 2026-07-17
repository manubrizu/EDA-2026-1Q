package util;

import java.text.Normalizer;

public class InputNormalizer {

    public static String normalize(String input) {
        if (input == null) {
            return "";
        }

        String normalized = input.trim().toLowerCase();

        normalized = Normalizer.normalize(normalized, Normalizer.Form.NFD);
        normalized = normalized.replaceAll("\\p{M}", "");

        normalized = normalized.replace('ñ', 'n');
        normalized = normalized.replaceAll("\\s+", " ");

        return normalized;
    }
}