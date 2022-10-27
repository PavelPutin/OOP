package ru.vsu.putin_p_a.web_server.servlet_server.servlets;

import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Controller;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Get;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.WebController;

@WebController("")
public class DefaultNotFound implements Controller {

    @Get("")
    public String doGet() {
        return """
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Page not found</title>
                </head>
                <body><h1>Error 404: Page not found</h1></body>
                </html>""";
    }
}
