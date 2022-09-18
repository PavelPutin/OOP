package Task1.WebServer.socket_server;

import Task1.WebServer.Server;
import Task1.WebServer.configuration.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer implements Server {

    private final Configuration configuration;

    public FileServer() throws FileNotFoundException {
        configuration = new Configuration();
    }

    @Override
    public void start() {
        try (ServerSocket server = new ServerSocket(configuration.getPort())) {
            System.out.println(String.format("Server started at port %d...", configuration.getPort()));
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
