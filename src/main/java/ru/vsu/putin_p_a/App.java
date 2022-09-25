package ru.vsu.putin_p_a;

import ru.vsu.putin_p_a.web_server.Server;
import ru.vsu.putin_p_a.web_server.servlet_server.WebContainer;
import ru.vsu.putin_p_a.web_server.socket_server.FileServer;

import java.io.IOException;

public class App {
    public static final String ROOT = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    public static final String CONFIGURATION = ROOT + "file_server_configuration.properties";
    public static void main(String[] args) {
        try {
            Server fs = new WebContainer();
            fs.start();
        } catch (IOException e) {
            System.out.println("Can't run app");
            e.printStackTrace();
        }
    }
}
