package ru.vsu.putin_p_a.web_server.http_protocol;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {
    private final String text;
    private final String method;
    private final String uri;
    private final Map<String, String> parameters;

    public HttpRequest(String text) throws HttpError {
        this.text = text;
        method = parseMethod();
        uri = parseUri();
        parameters = parseParameters();
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getText() {
        return text;
    }

    public String getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    // todo: переместить parse методы отсюда

    private String parseMethod() throws HttpError {
        try {
            String parsed = new Scanner(text).next();
            boolean unavailable = true;
            for (Methods m : Methods.values()) {
                if (parsed.equals(m.toString())) {
                    unavailable = false;
                    break;
                }
            }
            if (unavailable) {
                throw new HttpError("Unavailable method");
            }
            return parsed;
        } catch (NoSuchElementException e) {
            throw new HttpError("Request doesn't have method");
        }
    }

    private String parseUri() throws HttpError {
        try {
            Scanner s = new Scanner(text);
            s.useDelimiter("\s|\\?");
            String parsed = "";
            for (int i = 0; i < 2; i++) {
                parsed = s.next();
            }

            return parsed;
        } catch (NoSuchElementException e) {
            throw new HttpError("Can't parse uri");
        }
    }

    private Map<String, String> parseParameters() throws HttpError {
        try {
            Map<String, String> parsed = new HashMap<>();
            String startLine = new Scanner(text).nextLine();
            Matcher matcher = Pattern.compile("(?<=[?&])\\w+=[^&\s]+").matcher(startLine);
            while (matcher.find()) {
                String parameter = matcher.group();
                String[] split = parameter.split("=");
                parsed.put(split[0], split[1]);
            }
            return parsed;
        } catch (NoSuchElementException e) {
            throw new HttpError("Can't parse parameters");
        }
    }
}
