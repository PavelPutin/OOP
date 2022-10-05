package ru.vsu.putin_p_a.web_server.servlet_server.servlets;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface WebServlet {
    String value();
}
