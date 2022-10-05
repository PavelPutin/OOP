package ru.vsu.putin_p_a.web_server.socket_server;

import ru.vsu.putin_p_a.App;
import ru.vsu.putin_p_a.web_server.Server;
import ru.vsu.putin_p_a.web_server.configuration.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer implements Server {

    private final Configuration configuration;

    public FileServer() throws IOException {
        configuration = new Configuration();
        configuration.load(App.CONFIGURATION);
    }

    @Override
    public void start() {
        try (ServerSocket server = new ServerSocket(configuration.getPort())) {
            App.LOGGING.printf("Server started at port %d...%n", configuration.getPort());
            while (true) {
                Socket client = server.accept();
                /*@Thanks FelixDes*/
                new Thread(new SocketProcess(client, configuration)).start();
            }
        } catch (IOException e) {
            App.LOGGING.println("Can't start server");
            e.printStackTrace();
        }
    }
}
