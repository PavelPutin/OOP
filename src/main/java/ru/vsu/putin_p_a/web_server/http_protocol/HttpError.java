package ru.vsu.putin_p_a.web_server.http_protocol;

public class HttpError extends Exception{
    public HttpError() {
        super();
    }

    public HttpError(String message) {
        super(message);
    }
}
