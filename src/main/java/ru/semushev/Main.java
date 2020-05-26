package ru.semushev;

import ru.semushev.entity.CSVEntity;
import ru.semushev.entity.CSVParser;
import ru.semushev.entity.FileUtils;
import ru.semushev.entity.IncomingParams;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        IncomingParams incomingParams = new IncomingParams(args);
        List<CSVEntity> csvEntities = CSVParser.parse(incomingParams.getFileName(), incomingParams.getSeparator(), incomingParams.getScope());
        String fileName = incomingParams.getFileName();
        fileName = fileName.substring(0, fileName.length() - 3);
        fileName += "txt";
        FileUtils.writeToFile(fileName, csvEntities);
    }
}
