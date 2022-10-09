package ru.vsu.putin_p_a.web_server.servlet_server;

import ru.vsu.putin_p_a.App;
import ru.vsu.putin_p_a.parser.RequestParser;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpError;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpRequest;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpResponse;
import ru.vsu.putin_p_a.web_server.servlet_server.servlets.Servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.text.ParseException;

public class ClientRequest implements Runnable {

    private final Socket client;

    public ClientRequest(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        App.LOGGING.println("Начата обработка клиентского запроса");
        try {
            RequestParser parser = new RequestParser(client.getInputStream());
            String content = parser.parse();

            HttpRequest req = new HttpRequest(content);
            HttpResponse resp = new HttpResponse();

            Servlet mapped = WebContainer.servletMapper.getServletOrNotFound(req.getUri());
            Method subApplication = WebContainer.servletMapper.getSubApplication(req.getUri());
            mapped.init();

            if (req.getMethod().equals("GET")) {
                App.LOGGING.println("Начата обработка GET запроса");
                mapped.doGet(req, resp, subApplication);
                App.LOGGING.println("Обработка GET запроса завершена");
            }

            App.LOGGING.println("Response is ready");
            resp.send(client.getOutputStream());
            App.LOGGING.println("Response was sent");
            mapped.destroy();
        } catch (IOException e) {
            processIOException(e);
        } catch (HttpError | ParseException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            App.LOGGING.println(e.getMessage());
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                App.LOGGING.println("Can't close the connection");
            }
        }
    }

    private static void processIOException(IOException e) {
        App.LOGGING.println("There was a problem with data input/output");
        e.printStackTrace();
    }
}
