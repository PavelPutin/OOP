package ru.vsu.putin_p_a.web_server.servlet_server.servlets;

import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Controller;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Get;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.IExceptionController;

@Controller("/app/hello/fileNotFound")
public class AppHelloFileNotFoundController implements IExceptionController {

    @Override
    @Get("")
    public String showErrorPage(Exception e) {
        return """
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>File not found</title>
                </head>
                <body><h1>Selected file not found</h1></body>
                </html>""";
    }
}
