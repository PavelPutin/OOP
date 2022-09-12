package Task1.WebServer;

import Task1.Validators.FileAccessForReadingValidator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileServer {
    private final Pattern REQUESTED_URL = Pattern.compile("(?<=GET\\s)/.*(?=\\sHTTP/1.1)");

    private Configuration configuration;
    private ServerSocket server;

    public FileServer() {
        try {
            configuration = new Configuration();
            server = new ServerSocket(configuration.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            while (true) {

                System.out.println("Server started...");
                Socket client = server.accept();
                System.out.println("Socket accepted...");

                Scanner input = new Scanner(client.getInputStream());
                String startingLine = input.nextLine();
                System.out.printf("Starting line: %s%n", startingLine);
                Path url = Paths.get(getUrl(startingLine));
                Path appName = url.getParent();

                if (!appName.equals(Paths.get("/app/hello"))) {
                    return;
                }

                Path targetName = url.getFileName();
                Path target = configuration.getRoot().resolve(targetName);

                System.out.printf("Try to send file %s%n", target);

                new FileAccessForReadingValidator(target.toFile()).validate();

                FileInputStream fis = new FileInputStream(target.toFile());
                byte[] content = fis.readAllBytes();

                String responce = "HTTP 200 OK\r\n" +
                        "Content-length: " + content.length + "\r\n" +
                        "Connection: close\r\n\r\n";

                OutputStream os = client.getOutputStream();
                os.write(responce.getBytes());
                os.write(content);
                os.flush();
                System.out.println("File was sended.");

                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getAppNameFromURL(String url) {
        return null;
    }

    private String getFileNameFromURL(String url) {
        Pattern fileName = Pattern.compile("(?<=/).*(?=$)");
        Matcher matcher = fileName.matcher(url);
        if (matcher.find()) {
            return url.substring(matcher.start(), matcher.end());
        } else {
            return null;
        }
    }

    private String getUrl(String startingLine) {
        Matcher matcher = REQUESTED_URL.matcher(startingLine);
        String result = null;
        if (matcher.find()) {
            result = startingLine.substring(matcher.start(), matcher.end());
        }
        return result;
    }
}
