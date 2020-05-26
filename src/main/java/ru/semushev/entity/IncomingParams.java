package ru.semushev.entity;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class IncomingParams {
    private char separator;
    private char scope;
    private String fileName;

    public IncomingParams(String[] args) {
        separator = getParam(args, "separator", ',');
        scope = getParam(args, "scope", '"');
        fileName = findParam(args, "fileName");
        if (fileName == null) {
            throw new IllegalArgumentException("File name must be set");
        }
    }

    private char getParam(String[] args, String typeOfParam, char defaultChar) {
        String param = findParam(args, typeOfParam);
        if (param != null) {
            return getParam(param, typeOfParam);
        } else {
            return defaultChar;
        }
    }

    private String findParam(String[] args, String param) {
        String paramValue = Arrays.stream(args).filter(s -> s.startsWith(param + "='")).findFirst().orElse(null);
        if (paramValue != null) {
            paramValue = paramValue.substring(param.length() + 2);
            paramValue = paramValue.substring(0, paramValue.length() - 1);
        }
        return paramValue;
    }

    private char getParam(String param, String typeOfParam) {
        if (param.length() != 1) {
            throw new IllegalArgumentException("Illegal param: " + typeOfParam);
        } else {
            return param.charAt(0);
        }
    }
}
