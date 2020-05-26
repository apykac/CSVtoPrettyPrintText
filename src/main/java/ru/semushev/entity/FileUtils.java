package ru.semushev.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {
    public static void writeToFile(String fileName, List<CSVEntity> csvEntities) throws IOException {
        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(fileName))) {
            StringBuilder builder = new StringBuilder();
            for (CSVEntity csvEntity : csvEntities) {
                builder.append(csvEntity.toString()).append("*".repeat(25)).append("\n");
            }
            writer.write(builder.toString().getBytes(StandardCharsets.UTF_8));
        }
    }
}
