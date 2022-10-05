package ru.vsu.putin_p_a.web_server.servlet_server;

import ru.vsu.putin_p_a.App;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpError;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpRequest;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpResponse;
import ru.vsu.putin_p_a.parser.ByteToStringParser;
import ru.vsu.putin_p_a.web_server.http_protocol.ResponseStatus;
import ru.vsu.putin_p_a.web_server.servlet_server.mapper.ServletMapException;
import ru.vsu.putin_p_a.web_server.servlet_server.servlets.Servlet;
import ru.vsu.putin_p_a.web_server.servlet_server.servlets.ServletException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientRequest implements Runnable {

    private final Socket client;
    private final WebContainer server;

    public ClientRequest(Socket client, WebContainer server) {
        this.client = client;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            App.LOGGING.println("Начата обработка клиентского запроса");
            ByteToStringParser parser = new ByteToStringParser(client.getInputStream());
            String content = parser.parse();

            System.out.println(content);

            HttpRequest req = new HttpRequest(content);
            Servlet mapped = server.getServletMapper().getServlet(req.getUri());
            mapped.init();
            App.LOGGING.println("Сервлета проинициализирована");
            HttpResponse resp = new HttpResponse();

            if (req.getMethod().equals("GET")) {
                App.LOGGING.println("Начата обработка GET запроса");
                mapped.doGet(req, resp);
                App.LOGGING.println("Обработка GET запроса завершена");
            }
            App.LOGGING.println("Response is ready");
            resp.send(client.getOutputStream());
            App.LOGGING.println("Response was sent");
            mapped.destroy();
        } catch (IOException e) {
            App.LOGGING.println("There was a problem with data input/output");
            e.printStackTrace();
        } catch (ServletMapException e) {
            App.LOGGING.println("Can't get servlet");
            e.printStackTrace();
        } catch (ServletException e) {
            App.LOGGING.println("Servlets initialization error");
            e.printStackTrace();
        } catch (HttpError e) {
            throw new RuntimeException(e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                App.LOGGING.println("Can't close the connection");
            }
        }
    }
}
