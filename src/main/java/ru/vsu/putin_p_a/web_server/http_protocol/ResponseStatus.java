package ru.vsu.putin_p_a.web_server.http_protocol;

/**
 * Цель хранит допустимые статусы ответа
 */
public enum ResponseStatus {
    OK (200, "OK"),
    NOT_FOUND (404, "Not Found");

    private final int code;
    private final String status;

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
