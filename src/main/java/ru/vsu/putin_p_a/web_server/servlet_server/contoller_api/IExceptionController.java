package ru.vsu.putin_p_a.web_server.servlet_server.contoller_api;

public interface IExceptionController extends IController {
    String showErrorPage(Exception e);
}
