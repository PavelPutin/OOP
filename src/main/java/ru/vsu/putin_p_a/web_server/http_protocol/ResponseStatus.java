package ru.vsu.putin_p_a.web_server.http_protocol;

public enum ResponseStatus {
    OK (200, "OK"),
    NOT_FOUND (404, "Not Found"),
    BAD_REQUEST(400, "Bad Request");

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
