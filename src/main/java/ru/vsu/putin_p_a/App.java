package ru.vsu.putin_p_a;

import ru.vsu.putin_p_a.web_server.Server;
import ru.vsu.putin_p_a.web_server.servlet_server.WebContainer;
import ru.vsu.putin_p_a.web_server.socket_server.FileServer;
import ru.vsu.putin_p_a.web_server.socket_server.SocketProcess;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class App {
    public static final String ROOT = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    public static final String CONFIGURATION = ROOT + "file_server_configuration.properties";
    public static final PrintStream LOGGING = System.out;
    public static void main(String[] args) {
        try {
            Server fs = new WebContainer();
            fs.start();
        } catch (IOException e) {
            App.LOGGING.println(e.getMessage());
        }
    }
}
