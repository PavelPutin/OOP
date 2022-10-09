package ru.vsu.putin_p_a.web_server.configuration;

import ru.vsu.putin_p_a.App;
import ru.vsu.putin_p_a.validators.file_validators.DirectoryAccessForReadingValidator;
import ru.vsu.putin_p_a.validators.file_validators.FileAccessForReadingValidator;
import ru.vsu.putin_p_a.validators.file_validators.ValidationException;
import ru.vsu.putin_p_a.validators.parameter_validators.ParameterExistsValidator;
import ru.vsu.putin_p_a.validators.parameter_validators.PortValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Configuration {
    public static final String ROOT = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    public static final String SOURCE = ROOT + "file_server_configuration.properties";
    private Integer port;
    private Path root;
    private String appName;

    private String servletsPackage;

    public void load(String configuration) throws IOException {
        File source = new File(configuration);
        new FileAccessForReadingValidator(source).validate();

        Properties properties = new Properties();
        properties.load(new FileInputStream(source));
        port = parsePort(properties.getProperty("port"));
        App.LOGGING.println(port);
        root = parseRoot(properties.getProperty("root"));
        App.LOGGING.println(root);
        appName = parseAppName(properties.getProperty("appname"));
        servletsPackage = parseServletsPackage(properties.getProperty("servlets-package"));
        App.LOGGING.println(servletsPackage);
    }

    public Integer getPort() {
        return port;
    }

    public Path getRoot() {
        return root;
    }

    public String getAppName() {
        return appName;
    }

    public String getServletsPackage() {
        return servletsPackage;
    }

    private Path parseRoot(String input) throws ValidationException {
        new ParameterExistsValidator("root", input).validate();
        Path inputRoot = Paths.get(input);
        new DirectoryAccessForReadingValidator(inputRoot.toFile()).validate();
        return inputRoot;
    }

    private Integer parsePort(String input) throws ValidationException {
        new ParameterExistsValidator("input", input).validate();
        new PortValidator(input).validate();
        return Integer.valueOf(input);
    }

    private String parseAppName(String input) throws ValidationException {
        new ParameterExistsValidator("AppName", input).validate();
        return input;
    }

    private String parseServletsPackage(String input) throws ValidationException {
        new ParameterExistsValidator("Package", input).validate();
        return input;
    }
}
