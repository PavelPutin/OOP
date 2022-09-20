package ru.vsu.putin_p_a.web_server.configuration;

import ru.vsu.putin_p_a.validators.file_validators.DirectoryAccessForReadingValidator;
import ru.vsu.putin_p_a.validators.file_validators.FileAccessForReadingValidator;
import ru.vsu.putin_p_a.validators.file_validators.ValidationException;
import ru.vsu.putin_p_a.validators.port_validator.PortValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Configuration {
    private final Properties properties;
    private Integer port;
    private Path root;
    private String appName;

    public Configuration() {
        properties = new Properties();
    }

    public void load(String configuration) throws IOException {
        File source = new File(configuration);
        new FileAccessForReadingValidator(source).validate();

        properties.load(new FileInputStream(source));
        port = parsePort(properties.getProperty("port"));
        root = parseRoot(properties.getProperty("root"));
        appName = parseAppName(properties.getProperty("appname"));
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

    private Path parseRoot(String input) throws ValidationException {
        Path inputRoot = Paths.get(input);
        new DirectoryAccessForReadingValidator(inputRoot.toFile()).validate();
        return inputRoot;
    }

    private Integer parsePort(String input) throws ValidationException {
        new PortValidator(input).validate();
        return Integer.valueOf(input);
    }

    private String parseAppName(String value) {
        return value;
    }
}
