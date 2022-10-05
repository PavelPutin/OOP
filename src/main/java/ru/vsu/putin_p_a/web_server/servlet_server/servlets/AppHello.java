package ru.vsu.putin_p_a.web_server.servlet_server.servlets;

import ru.vsu.putin_p_a.App;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpRequest;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpResponse;
import ru.vsu.putin_p_a.web_server.http_protocol.ResponseStatus;

import java.io.PrintWriter;

@WebServlet("/app/hello")
public class AppHello implements Servlet {
    @Override
    public void init() throws ServletException {
        Servlet.super.init();
        App.LOGGING.println("Сервлета /app/hello проинициализирована");
    }

    @Override
    public void doGet(HttpRequest req, HttpResponse resp) {
        PrintWriter pw = new PrintWriter(resp.getOutputStream());
        pw.println("Hello, servlet!");
        resp.setStatus(ResponseStatus.OK);
        pw.close();
    }
//    /app/hello/file?name=source.txt
//    @Get("file")
//    public int add(@Param("name") String name) {
//        return 0;
//    }
//
//    public int multiply(int a, int b) {
//        return 0;
//    }
}
