package ru.vsu.putin_p_a.web_server.servlet_server.servlets;

import ru.vsu.putin_p_a.web_server.http_protocol.HttpRequest;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpResponse;

public interface Servlet {
    default void init() throws ServletException {}
    void doGet(HttpRequest req, HttpResponse resp);
    default void destroy() {}
}
