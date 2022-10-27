package ru.vsu.putin_p_a.web_server.servlet_server.servlets;

import ru.vsu.putin_p_a.App;
import ru.vsu.putin_p_a.validators.file_validators.DirectoryAccessForReadingValidator;
import ru.vsu.putin_p_a.validators.file_validators.FileAccessForReadingValidator;
import ru.vsu.putin_p_a.validators.file_validators.ValidationException;
import ru.vsu.putin_p_a.validators.parameter_validators.ParameterExistsValidator;
import ru.vsu.putin_p_a.web_server.configuration.Configuration;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpRequest;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpResponse;
import ru.vsu.putin_p_a.web_server.http_protocol.ResponseStatus;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Controller;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Get;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.Param;
import ru.vsu.putin_p_a.web_server.servlet_server.contoller_api.WebController;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebController("/app/hello")
public class AppHello implements Controller {
    private final Configuration configuration = new AppHelloConfiguration();
    private final Path root;

    public AppHello(){
        try {
            configuration.load(Configuration.ROOT);
            root = parseRoot(configuration.getProperties().getProperty("root"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Get("/file")
    public byte[] sendFile(@Param("file") String file) {
        Path fullPath = root.resolve(file);
        try (FileInputStream fis = new FileInputStream(fullPath.toFile())) {
            return fis.readAllBytes();
        } catch (FileNotFoundException e) {
            return "Файл не найден".getBytes();
        } catch (IOException e) {
            return "Неполучилось прочитать файл".getBytes();
        }
    }

//    @Get("file")
//    public byte[] sendFile(@Param("file") String file) {
//        return new byte[4];
//    }

    private Path parseRoot(String input) throws ValidationException {
        new ParameterExistsValidator("root", input).validate();
        Path inputRoot = Paths.get(input);
        new DirectoryAccessForReadingValidator(inputRoot.toFile()).validate();
        return inputRoot;
    }
}