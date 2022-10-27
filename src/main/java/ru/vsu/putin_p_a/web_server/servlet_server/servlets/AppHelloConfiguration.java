package ru.vsu.putin_p_a.web_server.servlet_server.servlets;

import ru.vsu.putin_p_a.validators.file_validators.DirectoryAccessForReadingValidator;
import ru.vsu.putin_p_a.validators.file_validators.FileAccessForReadingValidator;
import ru.vsu.putin_p_a.validators.file_validators.ValidationException;
import ru.vsu.putin_p_a.validators.parameter_validators.ParameterExistsValidator;
import ru.vsu.putin_p_a.web_server.configuration.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class AppHelloConfiguration implements Configuration {
    private Properties properties;
    @Override
    public void load(String configuration) throws IOException {
        File source = new File(configuration + "/appHello.properties");
        new FileAccessForReadingValidator(source).validate();

        properties = new Properties();
        properties.load(new FileInputStream(source));
    }

    @Override
    public Properties getProperties() {
        return properties;
    }
}
