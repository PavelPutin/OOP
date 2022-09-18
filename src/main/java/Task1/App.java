package Task1;

import Task1.WebServer.Server;
import Task1.WebServer.socket_server.FileServer;

import java.io.FileNotFoundException;

public class App {
    public static void main(String[] args) {
        try {
            Server fs = new FileServer();
            fs.start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
