package ru.vsu.putin_p_a.web_server.servlet_server.servlets;

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

public class AppHelloConfiguration implements Configuration {
    private Path root;

    @Override
    public void load(String configuration) throws IOException {
        File source = new File(configuration);
        new FileAccessForReadingValidator(source).validate();

        Properties properties = new Properties();
        properties.load(new FileInputStream(source));
        root = parseRoot(properties.getProperty("root"));
    }

    public Path getRoot() {
        return root;
    }

    private Path parseRoot(String input) throws ValidationException {
        new ParameterExistsValidator("root", input).validate();
        Path inputRoot = Paths.get(input);
        new DirectoryAccessForReadingValidator(inputRoot.toFile()).validate();
        return inputRoot;
    }
}
