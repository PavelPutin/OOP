package ru.vsu.putin_p_a.web_server.servlet_server.servlets;

import ru.vsu.putin_p_a.App;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpRequest;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpResponse;
import ru.vsu.putin_p_a.web_server.http_protocol.ResponseStatus;

import java.io.PrintWriter;

public class DefaultNotFound extends Servlet {
    public DefaultNotFound(HttpRequest req, HttpResponse resp) {
        super(req, resp);
    }

    @Override
    public void init() {
        App.LOGGING.println("Было обращение к несуществующей сервлете");
    }

    @Get("")
    public void doGet(HttpRequest req, HttpResponse resp) {
        resp.setStatus(ResponseStatus.NOT_FOUND);
        PrintWriter pw = new PrintWriter(resp.getOutputStream());
        pw.printf("""
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Page not found</title>
</head>
<body><h1>Error 404: Page %s not found</h1></body>
</html>""", req.getUri());
        pw.close();
    }
}
