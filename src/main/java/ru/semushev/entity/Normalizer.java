package ru.semushev.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Normalizer {
    private static final Map<String, String> REPLACEMENT = new HashMap<>();

    static {
        REPLACEMENT.put("\\n", "\n");
        REPLACEMENT.put("\\t", "\t");
        REPLACEMENT.put("\\r", "\t");
    }

    public static String normalizeString(String s) {
        StringWrapper sw = new StringWrapper(s);
        REPLACEMENT.forEach((k, v) -> sw.value = sw.value.replace(k, v));
        return sw.value;
    }

    @AllArgsConstructor
    private static class StringWrapper {
        private String value;
    }
}
