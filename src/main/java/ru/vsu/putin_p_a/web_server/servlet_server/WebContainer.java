package ru.vsu.putin_p_a.web_server.servlet_server;

import ru.vsu.putin_p_a.App;
import ru.vsu.putin_p_a.web_server.Server;
import ru.vsu.putin_p_a.web_server.configuration.Configuration;
import ru.vsu.putin_p_a.web_server.servlet_server.mapper.ServletMapException;
import ru.vsu.putin_p_a.web_server.servlet_server.mapper.ServletMapper;
import ru.vsu.putin_p_a.web_server.servlet_server.servlets.DefaultNotFound;
import ru.vsu.putin_p_a.web_server.servlet_server.servlets.Servlet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebContainer implements Server {
    public static final Configuration configuration = new Configuration();;
    public static final ServletMapper servletMapper = new ServletMapper();;

    public WebContainer() throws IOException, ServletMapException {
        configuration.load(Configuration.SOURCE);
        servletMapper.mapServlet(configuration.getServletsPackage());
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public ServletMapper getServletMapper() {
        return servletMapper;
    }

    @Override
    public void start() {
        try (ServerSocket server = new ServerSocket(configuration.getPort())) {
            App.LOGGING.printf("Server started on port %d%n", configuration.getPort());
            while (true) {
                Socket client = server.accept();
                App.LOGGING.printf("Accepted socket %s%n", client.getInetAddress());
                new Thread(new ClientRequest(client)).start();
            }
        } catch (IOException e) {
            App.LOGGING.println("Can't start server");
            App.LOGGING.println(e.getMessage());
        }
    }
}
