package ru.vsu.putin_p_a.web_server.servlet_server.mapper;

import ru.vsu.putin_p_a.web_server.http_protocol.ResponseStatus;

public class ControllerExceptionContainer extends RuntimeException {

    private final String handlerUri;
    private final ResponseStatus status;

    public ControllerExceptionContainer(Throwable cause, String handlerUri, ResponseStatus status) {
        super(cause);
        this.handlerUri = handlerUri;
        this.status = status;
    }

    public String getHandlerUri() {
        return handlerUri;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return "container";
    }
}
