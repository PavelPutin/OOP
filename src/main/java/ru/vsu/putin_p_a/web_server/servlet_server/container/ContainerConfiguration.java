package ru.vsu.putin_p_a.web_server.servlet_server.container;

import ru.vsu.putin_p_a.validators.file_validators.FileAccessForReadingValidator;
import ru.vsu.putin_p_a.web_server.configuration.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ContainerConfiguration implements Configuration {
    private final Properties properties = new Properties();

    @Override
    public void load(String configuration) throws IOException {
        File source = new File(configuration);
        new FileAccessForReadingValidator(source).validate();

        try (FileInputStream fis = new FileInputStream(source)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new IOException("Can't get external parameters");
        }
    }

    @Override
    public Properties getProperties() {
        return properties;
    }
}
