package ru.vsu.putin_p_a.web_server.servlet_server.servlets;

import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Controller;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Get;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.IExceptionController;

@Controller("math/intdivision/zerodivision")
public class MathIntDivisionExceptionController implements IExceptionController {
    @Override
    @Get("")
    public String showErrorPage(Exception e) {
        return """
                <html lang="ru">
                <head>
                    <meta charset="UTF-8">
                    <title>Деление на ноль</title>
                </head>
                <body><h1>На ноль делить нельзя</h1></body>
                </html>""";
    }
}
