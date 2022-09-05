package WebServer;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        int port = 8080;
        try {
            FileServer fs = new FileServer(port);
            fs.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
