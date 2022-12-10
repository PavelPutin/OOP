package ru.vsu.putin_p_a.parser;

import ru.vsu.putin_p_a.App;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestParser {
    private final InputStream source;

    public RequestParser(InputStream source) {
        this.source = source;
    }

    public String parse() throws ParseException {
        StringBuilder parsed = new StringBuilder();
        App.LOGGING.println("Parsing was started.");
        int totalBytes = 0;
        try {
            while (!parsed.toString().contains("\r\n\r\n")) {
                parsed.append((char) source.read());
                totalBytes++;
            }

            Matcher contentLengthHeader = Pattern.compile("(?<=Content-length: )\\d+$").matcher(parsed.toString());
            int contentLength = contentLengthHeader.find() ?
                    Math.max(Integer.parseInt(contentLengthHeader.group()), 0) :
                    0;

            for (int bytesRead = 0; bytesRead < contentLength; bytesRead++) {
                parsed.append((char) source.read());
                totalBytes++;
            }

            App.LOGGING.println("Parsing was completed.");
            return parsed.toString();
        } catch (IOException e) {
            throw new ParseException("The request parsing error occurred.", totalBytes);
        }
    }
}
