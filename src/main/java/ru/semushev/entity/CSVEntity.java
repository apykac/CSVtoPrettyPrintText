package ru.semushev.entity;

import java.util.HashMap;
import java.util.Map;

public class CSVEntity {
    private Map<String, String> map = new HashMap<>();

    public void addParam(String key, String value) {
        map.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        map.forEach((k, v) -> builder.append(k).append(" : \n\t").append(Normalizer.normalizeString(v)).append("\n"));
        return builder.toString();
    }
}
