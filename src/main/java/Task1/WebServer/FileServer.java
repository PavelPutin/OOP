package Task1.WebServer;

import Task1.WebServer.configuration.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {

    private final Configuration configuration;

    public FileServer() throws FileNotFoundException {
        configuration = new Configuration();
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(configuration.getPort())) {
            System.out.println("Server started...");
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
