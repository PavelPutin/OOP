package ru.vsu.putin_p_a.web_server.servlet_server.mapper;

public class ServletAccessException extends Exception {
    public ServletAccessException() {
        super();
    }

    public ServletAccessException(String message) {
        super(message);
    }

    public ServletAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServletAccessException(Throwable cause) {
        super(cause);
    }

    protected ServletAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
