package ru.vsu.putin_p_a.web_server.servlet_server.container;

import ru.vsu.putin_p_a.validators.file_validators.DirectoryAccessForReadingValidator;
import ru.vsu.putin_p_a.validators.file_validators.FileAccessForReadingValidator;
import ru.vsu.putin_p_a.validators.file_validators.ValidationException;
import ru.vsu.putin_p_a.validators.parameter_validators.ParameterExistsValidator;
import ru.vsu.putin_p_a.validators.parameter_validators.PortValidator;
import ru.vsu.putin_p_a.web_server.configuration.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Цель: получить из файла конфигурации данные и привести их к виду, который нужен серверу
 * TODO: нарушает open/closed (low coupling) - этот класс знает семантику сервера
 * TODO: нарушает single responsibility - не только получает данные из файла, но и приводит их к некоторому виду, **предполагается** мной
 */
public class ContainerConfiguration implements Configuration {

    private Integer port;
    private String servletsPackage;

    @Override
    public void load(String configuration) throws IOException {
        File source = new File(configuration);
        new FileAccessForReadingValidator(source).validate();

        Properties properties = new Properties();
        properties.load(new FileInputStream(source));
        port = parsePort(properties.getProperty("port"));
        servletsPackage = parseServletsPackage(properties.getProperty("servlets-package"));
    }

    public Integer getPort() {
        return port;
    }

    public String getServletsPackage() {
        return servletsPackage;
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
