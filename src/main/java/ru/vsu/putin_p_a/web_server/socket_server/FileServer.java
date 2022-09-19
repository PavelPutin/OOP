package ru.vsu.putin_p_a.web_server.socket_server;

import ru.vsu.putin_p_a.web_server.Server;
import ru.vsu.putin_p_a.web_server.configuration.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer implements Server {
    public final String ROOT_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private final Configuration configuration;

    public FileServer() throws IOException {
        configuration = new Configuration();
        String configurationFileName = "file_server_configuration.properties";
        String configPath = ROOT_PATH + configurationFileName;
        configuration.load(configPath);
    }

    @Override
    public void start() {
        try (ServerSocket server = new ServerSocket(configuration.getPort())) {
            System.out.printf("Server started at port %d...%n", configuration.getPort());
            while (true) {
                Socket client = server.accept();
                /*@Thanks FelixDes*/
                new Thread(new SocketProcess(client, configuration)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
