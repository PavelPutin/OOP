package ru.vsu.putin_p_a.web_server.configuration;

import ru.vsu.putin_p_a.validators.file_validators.DirectoryAccessForReadingValidator;
import ru.vsu.putin_p_a.validators.file_validators.FileAccessForReadingValidator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;

public class Configuration {
    private Path configuration;
    private Properties properties;
    private Integer port;
    private Path root;
    private String appName;

    public Configuration() {
        properties = new Properties();
    }

    public void load(Path configuration) throws IOException {
        new FileAccessForReadingValidator(configuration.toFile()).validate();

        properties.load(new FileInputStream(configuration.toFile()));
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

    private Path parseRoot(String input) throws FileNotFoundException {
        Path inputRoot = Paths.get(input);
        new DirectoryAccessForReadingValidator(inputRoot.toFile()).validate();
        return inputRoot;
    }

    private Integer parsePort(String input) {
        return Integer.valueOf(input);
    }

    private String parseAppName(String value) {
        return value;
    }
}
