package ru.vsu.putin_p_a.web_server.servlet_server.container;

import ru.vsu.putin_p_a.App;
import ru.vsu.putin_p_a.validators.file_validators.ValidationException;
import ru.vsu.putin_p_a.validators.parameter_validators.ParameterExistsValidator;
import ru.vsu.putin_p_a.validators.parameter_validators.PortValidator;
import ru.vsu.putin_p_a.web_server.Server;
import ru.vsu.putin_p_a.web_server.configuration.Configuration;
import ru.vsu.putin_p_a.web_server.servlet_server.dispatcher.DispatcherServlets;
import ru.vsu.putin_p_a.web_server.servlet_server.mapper.ServletMapException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebContainer implements Server {
    public static final String SOURCE = Configuration.ROOT + "file_server_configuration.properties";
    private final DispatcherServlets dispatcherServlets = new DispatcherServlets();
    private final Integer port;

    public WebContainer() throws IOException, ServletMapException {
        Configuration configuration = new ContainerConfiguration();
        configuration.load(SOURCE);
        port = parsePort(configuration.getProperties().getProperty("port"));

        String servletsPackage = parseServletsPackage(configuration.getProperties().getProperty("servlets-package"));
        dispatcherServlets.loadMapper(servletsPackage);
    }

    @Override
    public void start() {
        try (ServerSocket server = new ServerSocket(port)) {
            App.LOGGING.printf("Server started on port %d%n", port);
            while (true) {
                Socket client = server.accept();
                App.LOGGING.printf("Accepted socket %s%n", client.getInetAddress());
                new Thread(() -> dispatcherServlets.formResponseFor(client)).start();
            }
        } catch (IOException e) {
            App.LOGGING.println("Can't start server");
            App.LOGGING.println(e.getMessage());
        }
    }

    private Integer parsePort(String input) throws ValidationException {
        new ParameterExistsValidator("input", input).validate();
        new PortValidator(input).validate();
        return Integer.valueOf(input);
    }

    private String parseServletsPackage(String input) throws ValidationException {
        new ParameterExistsValidator("Package", input).validate();
        return input;
    }
}
