package Task1;

import Task1.WebServer.FileServer;

public class App {
    public static void main(String[] args) {
        FileServer fs = new FileServer();
        fs.start();
    }
}
