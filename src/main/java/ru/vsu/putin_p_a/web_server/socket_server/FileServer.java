package ru.vsu.putin_p_a.web_server.socket_server;

import ru.vsu.putin_p_a.web_server.Server;
import ru.vsu.putin_p_a.web_server.configuration.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;

public class FileServer implements Server {
    private final Configuration configuration;

    public FileServer() throws IOException {
        configuration = new Configuration();
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String configurationFileName = "file_server_configuration.properties";
        String configPath = rootPath + configurationFileName;
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
