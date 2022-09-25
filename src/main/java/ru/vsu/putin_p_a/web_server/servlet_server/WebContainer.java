package ru.vsu.putin_p_a.web_server.servlet_server;

import ru.vsu.putin_p_a.App;
import ru.vsu.putin_p_a.web_server.Server;
import ru.vsu.putin_p_a.web_server.configuration.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebContainer implements Server {
    private Configuration configuration;
    private ServletMapper servletMapper;

    public WebContainer() throws IOException {
        this.configuration = new Configuration();
        this.configuration.load(App.CONFIGURATION);
        this.servletMapper = new ServletMapper();
        this.servletMapper.mapServlet(configuration.getServletsPackage());
    }

    @Override
    public void start() {
        try (ServerSocket server = new ServerSocket(configuration.getPort())) {
            while (true) {
                Socket client = server.accept();
                new Thread(new SocketParser(client, configuration)).start();
            }
        } catch (IOException e) {
            System.out.println("Can't start server");
            e.printStackTrace();
        }
    }
}
