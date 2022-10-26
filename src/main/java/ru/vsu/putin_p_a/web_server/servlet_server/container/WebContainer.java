package ru.vsu.putin_p_a.web_server.servlet_server.container;

import ru.vsu.putin_p_a.App;
import ru.vsu.putin_p_a.web_server.Server;
import ru.vsu.putin_p_a.web_server.configuration.Configuration;
import ru.vsu.putin_p_a.web_server.servlet_server.mapper.ServletMapException;
import ru.vsu.putin_p_a.web_server.servlet_server.mapper.ServletMapper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Цель: получить запрос клиента и отправить его в новый поток обработчика.
 */
public class WebContainer implements Server {
    public static final String SOURCE = Configuration.ROOT + "file_server_configuration.properties";
    public static final ContainerConfiguration configuration = new ContainerConfiguration();
    public static final ServletMapper servletMapper = new ServletMapper();

    public WebContainer() throws IOException, ServletMapException {
        configuration.load(SOURCE);
        servletMapper.mapServlet(configuration.getServletsPackage());
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
