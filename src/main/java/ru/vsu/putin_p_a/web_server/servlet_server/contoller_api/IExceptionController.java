package ru.vsu.putin_p_a.web_server.servlet_server.contoller_api;

import ru.vsu.putin_p_a.web_server.http_protocol.ResponseStatus;

public interface IExceptionController extends IController {
    String showErrorPage(Exception e, ResponseStatus status);
}
