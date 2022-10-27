package ru.vsu.putin_p_a.web_server.servlet_server.dispatcher;

import ru.vsu.putin_p_a.App;
import ru.vsu.putin_p_a.parser.RequestParser;
import ru.vsu.putin_p_a.web_server.http_protocol.*;
import ru.vsu.putin_p_a.web_server.servlet_server.mapper.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.text.ParseException;

public class DispatcherServlets {
    private final ControllerMapper mapper = new ControllerMapper();

    public void loadMapper(String servletsPackage) throws ServletMapException {
        mapper.mapServlet(servletsPackage);
    }

    public void formResponseFor(Socket client) {
        App.LOGGING.println("Начата обработка клиентского запроса");
        try {
            RequestParser parser = new RequestParser(client.getInputStream());
            String content = parser.parse();

            HttpRequest req = new HttpRequest(content);

            HttpResponse resp;
            if (req.getMethod().equals(Methods.GET.toString())) {
                resp = doGet(req);
            } else {
                resp = new HttpResponse();
                resp.setStatus(ResponseStatus.BAD_REQUEST);
                PrintWriter writer = new PrintWriter(resp.getOutputStream());
                writer.print("Несуществующий метод запроса");
                writer.close();
            }

            resp.send(client.getOutputStream());
        } catch (IOException e) {
            processIOException(e);
        } catch (HttpError | ParseException | MissingParametersException | InvocationTargetException |
                 IllegalAccessException | ServletAccessException e) {
            App.LOGGING.println(e.getMessage());
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                App.LOGGING.println("Can't close the connection");
            }
        }
    }

    private HttpResponse doGet(HttpRequest req) throws MissingParametersException, InvocationTargetException, IllegalAccessException, ServletAccessException, IOException {
        HttpResponse resp = new HttpResponse();
        resp.setStatus(ResponseStatus.NOT_FOUND);
        MappedApplication mappedApplication = mapper.getNotFound();
        if (mapper.containController(req)) {
            mappedApplication = mapper.getApplication(req);
            resp.setStatus(ResponseStatus.OK);
        }
        Object[] parameterValues = new Object[mappedApplication.parameterNames().size()];

        int i = 0;
        for (String parameterName : mappedApplication.parameterNames()) {
            parameterValues[i++] = req.getParameter(parameterName);
        }

        Object result = mappedApplication.task().invoke(mappedApplication.controller(), parameterValues);
        OutputStream outputStream = resp.getOutputStream();
        if (result instanceof byte[]) {
            outputStream.write((byte[]) result);
        } else {
            PrintWriter writer = new PrintWriter(outputStream);
            writer.print(result);
        }
        outputStream.close();
        return resp;
    }

    private static void processIOException(IOException e) {
        App.LOGGING.println("There was a problem with data input/output");
        e.printStackTrace();
    }
}
