package ru.vsu.putin_p_a.web_server.servlet_server.servlets;

import ru.vsu.putin_p_a.web_server.http_protocol.HttpRequest;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpResponse;

import java.lang.reflect.Method;

public interface Servlet {
    default void init() {}
    void doGet(HttpRequest req, HttpResponse resp, Method subApplication);
    default void destroy() {}
}
