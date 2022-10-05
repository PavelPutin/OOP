package ru.vsu.putin_p_a.parser;

import ru.vsu.putin_p_a.App;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ByteToStringParser {
    private final InputStream source;

    public ByteToStringParser(InputStream source) {
        this.source = source;
    }

    public String parse() throws IOException {
        StringBuilder parsed = new StringBuilder();
        App.LOGGING.println("Начат парсинг");
        while (!parsed.toString().contains("\r\n\r\n")) {
            parsed.append((char) source.read());
        }
        System.out.println(parsed);
        App.LOGGING.println("Произошёл парсинг");
        return parsed.toString();
    }
}
