package ru.vsu.putin_p_a;

import ru.vsu.putin_p_a.web_server.Server;
import ru.vsu.putin_p_a.web_server.servlet_server.WebContainer;

import java.io.PrintStream;

public class App {
    public static final String ROOT = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    public static final String CONFIGURATION = ROOT + "file_server_configuration.properties";
    public static final PrintStream LOGGING = System.out;
    public static void main(String[] args) {
        Server fs = new WebContainer();
        fs.start();
    }
}
