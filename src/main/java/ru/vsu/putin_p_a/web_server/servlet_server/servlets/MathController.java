package ru.vsu.putin_p_a.web_server.servlet_server.servlets;

import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Controller;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Get;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Param;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.WebController;

@WebController("/math")
public class MathController implements Controller {
    @Get("/sum")
    public int sum(@Param("a") String a, @Param("b") String b) {
        int aValue = Integer.parseInt(a), bValue = Integer.parseInt(b);
        return aValue + bValue;
    }

    @Get("/subtract")
    public int subtract(@Param("a") String a, @Param("b") String b) {
        int aValue = Integer.parseInt(a), bValue = Integer.parseInt(b);
        return aValue - bValue;
    }

    @Get("/powerOf2")
    public int powerOf2(@Param("n") String n) {
        return (int) Math.pow(2, Double.parseDouble(n));
    }
}
