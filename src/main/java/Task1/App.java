package Task1;

import Task1.WebServer.FileServer;

import java.io.FileNotFoundException;

public class App {
    public static void main(String[] args) {
        try {
            FileServer fs = new FileServer();
            fs.start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
