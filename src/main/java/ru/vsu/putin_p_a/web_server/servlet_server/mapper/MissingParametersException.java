package ru.vsu.putin_p_a.web_server.servlet_server.mapper;

public class MissingParametersException extends Exception {
    public MissingParametersException() {
        super();
    }

    public MissingParametersException(String message) {
        super(message);
    }

    public MissingParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingParametersException(Throwable cause) {
        super(cause);
    }

    protected MissingParametersException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
