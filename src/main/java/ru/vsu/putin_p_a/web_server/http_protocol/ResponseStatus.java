package ru.vsu.putin_p_a.web_server.http_protocol;

public enum ResponseStatus {
    OK (200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND (404, "Not Found"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type");

    private int code;
    private String status;

    ResponseStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}
