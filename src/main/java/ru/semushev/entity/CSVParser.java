package ru.semushev.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CSVParser {
    public static List<CSVEntity> parse(String path, char separator, char scope) throws IOException {
        List<CSVEntity> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String paramsString = reader.readLine();
            if (paramsString == null) {
                return result;
            }
            List<String> params = getSeparation(paramsString, separator, scope);
            for (String s = reader.readLine(); s != null; s = reader.readLine()) {
                s = s.trim();
                List<String> values = getSeparation(s, separator, scope);
                result.add(getCSVEntity(params, values));
            }
        }
        return result;
    }

    private static List<String> getSeparation(String s, char separator, char scope) {
        List<String> result = new ArrayList<>();
        s = s.trim();

        int i = s.charAt(0) == '"' ? 1 : 0;
        StringBuilder builder = new StringBuilder();
        while (i < s.length()) {
            int index = separation(s, i, separator, scope);
            if (index == -1) {
                builder.append(s.charAt(i));
                i++;
            } else {
                result.add(builder.toString());
                builder = new StringBuilder();
                i += index;
            }
        }
        if (s.charAt(s.length() - 1) == separator) result.add("");
        return result;
    }

    private static int separation(String s, int index, char separator, char scope) {
        boolean first = s.charAt(index) == scope &&
                ((index + 2 < s.length() && s.charAt(index + 1) == separator && s.charAt(index + 2) == scope) ||
                        (index + 1 < s.length() && s.charAt(index + 1) == separator) ||
                        (index + 1 == s.length()));
        boolean second = s.charAt(index) == separator &&
                ((index == 0) || (index + 1 == s.length()) ||
                        ((s.charAt(index - 1) == separator || s.charAt(index - 1) == scope) && (s.charAt(index + 1) == separator || s.charAt(index + 1) == scope)));

        if (first) {
            if (index + 2 < s.length() && s.charAt(index + 1) == separator && s.charAt(index + 2) == scope) return 3;
            else if (index + 2 < s.length() && s.charAt(index + 1) == separator && s.charAt(index + 2) == separator)                return 2;
            else if (index + 2 == s.length() && s.charAt(index + 1) == separator)                return 2;
            else return 1;
        } else if (second) {
            if (index + 1 < s.length() && s.charAt(index + 1) == scope) return 2;
            else if (index + 1 < s.length() && s.charAt(index + 1) == separator) return 1;
            else return 1;
        } else {
            return -1;
        }
    }

    private static CSVEntity getCSVEntity(List<String> params, List<String> values) {
        if (params.size() != values.size()) {
            throw new IllegalArgumentException("Some problem with parsing: size of value is not equal of param size: " + values + "; " + params);
        }

        CSVEntity csvEntity = new CSVEntity();
        for (int i = 0; i < params.size(); i++) {
            csvEntity.addParam(params.get(i), values.get(i));
        }
        return csvEntity;
    }
}
