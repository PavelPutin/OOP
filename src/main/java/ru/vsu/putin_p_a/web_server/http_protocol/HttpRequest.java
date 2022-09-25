package ru.vsu.putin_p_a.web_server.http_protocol;

import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String method;
    private final String uri;
    private final Map<String, String> parameters;

    public HttpRequest(String method, String uri) {
        this.method = method;
        this.uri = uri;
        this.parameters = new HashMap<>();
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getParameter(String parameter) {
        return parameters.get(parameter);
    }
}
