package ru.vsu.putin_p_a;

import ru.vsu.putin_p_a.web_server.Server;
import ru.vsu.putin_p_a.web_server.servlet_server.container.WebContainer;
import ru.vsu.putin_p_a.web_server.servlet_server.mapper.ServletMapException;

import java.io.IOException;
import java.io.PrintStream;

public class App {
    public static final PrintStream LOGGING = System.out;
    public static void main(String[] args) {
        try {
            Server fs = new WebContainer();
            fs.start();
        } catch (IOException | ServletMapException e) {
            App.LOGGING.println(e.getMessage());
        }
    }
}
