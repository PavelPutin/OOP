package ru.vsu.putin_p_a.web_server.servlet_server.servlets;

import ru.vsu.putin_p_a.web_server.http_protocol.ResponseStatus;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.IController;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Get;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Param;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Controller;
import ru.vsu.putin_p_a.web_server.servlet_server.mapper.ControllerExceptionContainer;

@Controller("/math")
public class MathController implements IController {
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

    @Get("/division")
    public double division(@Param("a") String a, @Param("b") String b) {
        double aValue = Double.parseDouble(a),
                bValue = Double.parseDouble(b);
        return aValue / bValue;
    }

    @Get("/intdivision")
    public int intdivision(@Param("a") String a, @Param("b") String b) {
        try {
            int aValue = Integer.parseInt(a),
                    bValue = Integer.parseInt(b);
            return aValue / bValue;
        } catch (NumberFormatException e) {
            throw new ControllerExceptionContainer(e, "math/number_format_exception", ResponseStatus.UNSUPPORTED_MEDIA_TYPE);
        } catch (ArithmeticException e) {
            throw new ControllerExceptionContainer(e, "math/intdivision/zerodivision", ResponseStatus.NOT_ACCEPTABLE);
        }
    }

    /*
    * Задача:
    * - добавить обработчик ошибок
    * - упаковать в jar (отдельно сервер, отдельно api контролеров)
    * - сделать новый проект, подключить сервер и api. Написать на js висилицу
    * */
}
