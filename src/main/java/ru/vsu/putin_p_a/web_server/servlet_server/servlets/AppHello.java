package ru.vsu.putin_p_a.web_server.servlet_server.servlets;

import ru.vsu.putin_p_a.validators.file_validators.DirectoryAccessForReadingValidator;
import ru.vsu.putin_p_a.validators.file_validators.ValidationException;
import ru.vsu.putin_p_a.validators.parameter_validators.ParameterExistsValidator;
import ru.vsu.putin_p_a.web_server.configuration.Configuration;
import ru.vsu.putin_p_a.web_server.http_protocol.ResponseStatus;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.IController;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Get;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Param;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Controller;
import ru.vsu.putin_p_a.web_server.servlet_server.mapper.ControllerExceptionContainer;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller("/app/hello")
public class AppHello implements IController {
    private final Path root;

    public AppHello(){
        try {
            Configuration configuration = new AppHelloConfiguration();
            configuration.load(Configuration.ROOT);
            root = parseRoot(configuration.getProperties().getProperty("root"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Get("/file")
    public byte[] sendFile(@Param("file") String file) throws ControllerExceptionContainer {
        Path fullPath = root.resolve(file);
        try (FileInputStream fis = new FileInputStream(fullPath.toFile())) {
            return fis.readAllBytes();
        } catch (IOException e) {
            throw new ControllerExceptionContainer(e, "/app/hello/fileNotFound", ResponseStatus.NOT_FOUND);
        }
    }


    private Path parseRoot(String input) throws ValidationException {
        new ParameterExistsValidator("root", input).validate();
        Path inputRoot = Paths.get(input);
        new DirectoryAccessForReadingValidator(inputRoot.toFile()).validate();
        return inputRoot;
    }
}