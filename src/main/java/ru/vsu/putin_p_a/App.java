package ru.vsu.putin_p_a;

import ru.vsu.putin_p_a.web_server.Server;
import ru.vsu.putin_p_a.web_server.socket_server.FileServer;

import java.io.FileNotFoundException;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {
            Server fs = new FileServer();
            fs.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
