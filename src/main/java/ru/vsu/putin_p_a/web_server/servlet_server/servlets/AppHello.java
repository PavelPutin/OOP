package ru.vsu.putin_p_a.web_server.servlet_server.servlets;

import ru.vsu.putin_p_a.App;
import ru.vsu.putin_p_a.validators.file_validators.FileAccessForReadingValidator;
import ru.vsu.putin_p_a.validators.file_validators.ValidationException;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpRequest;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpResponse;
import ru.vsu.putin_p_a.web_server.http_protocol.ResponseStatus;
import ru.vsu.putin_p_a.web_server.servlet_server.WebContainer;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@WebServlet("/app/hello")
public class AppHello implements Servlet {
    @Override
    public void init() {
        App.LOGGING.println("Сервлета /app/hello проинициализирована");
    }

    @Override
    public void doGet(HttpRequest req, HttpResponse resp) {
        String fileName = req.getParameter("file");
        if (fileName == null) {
            resp.setStatus(ResponseStatus.NOT_FOUND);
            PrintWriter pw = new PrintWriter(resp.getOutputStream());
            pw.println("""
                        <html>
                        <head>
                            <meta charset="UTF-8">
                        </head>
                        <body>Не передано имя файла</body>
                        </html>""");
            pw.close();
        } else {
            Path fullPath = WebContainer.configuration.getRoot().resolve(fileName);
            App.LOGGING.println(fullPath);
            try {
                new FileAccessForReadingValidator(fullPath.toFile()).validate();
                try (FileInputStream file = new FileInputStream(fullPath.toFile())) {
                    resp.setStatus(ResponseStatus.OK);
                    OutputStream out = resp.getOutputStream();
                    out.write(file.readAllBytes());
                    out.close();
                    App.LOGGING.printf("Файл %s прочитан%n", fullPath);
                }
            } catch (IOException e) {
                resp.setStatus(ResponseStatus.NOT_FOUND);
                PrintWriter pw = new PrintWriter(resp.getOutputStream());
                pw.println("""
                        <html>
                        <head>
                            <meta charset="UTF-8">
                        </head>
                        <body>Невозможно получить доступ к файлу! Проверьте имя файла и его настройки доступа.</body>
                        </html>""");
                pw.close();
            }
        }
    }
//    /app/hello/file?name=source.txt
//    @Get("file")
//    public int add(@Param("name") String name) {
//        return 0;
//    }
//
//    public int multiply(int a, int b) {
//        return 0;
//    }
}
