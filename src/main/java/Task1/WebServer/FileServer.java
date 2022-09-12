package Task1.WebServer;

import Task1.Validators.FileAccessForReadingValidator;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileServer implements Closeable {
    private final Pattern REQUESTED_URL = Pattern.compile("(?<=GET\\s)/.*(?=\\sHTTP/1.1)");

    private Configuration configuration;
    private ServerSocket server;

    public FileServer() throws FileNotFoundException {
        configuration = new Configuration();
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(configuration.getPort())) {
            this.server = server;
            System.out.println("Server started...");
            accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void accept() {
        while (true) {
            try (Socket client = server.accept()) {
                System.out.printf("Socket %s accepted...%n", client);

                Scanner input = new Scanner(client.getInputStream());
                System.out.println("Get scanner");
                String startingLine = input.nextLine();
                System.out.printf("Starting line: %s%n", startingLine);
                Path url = Paths.get(getUrl(startingLine));
                Path appName = url.getParent();
                System.out.println(appName);

                byte[] content;
                if (!appName.equals(Paths.get("/app/hello"))) {
                    content = "Not supported app!".getBytes();
                } else {
                    Path targetName = url.getFileName();
                    Path target = configuration.getRoot().resolve(targetName);

                    System.out.printf("Try to send file %s%n", target);

                    try {
                        new FileAccessForReadingValidator(target.toFile()).validate();

                        FileInputStream fis = new FileInputStream(target.toFile());
                        content = fis.readAllBytes();
                    } catch (Exception e) {
                        content = "There isn't such file".getBytes();
                    }
                }
                String responce = "HTTP 200 OK\r\n" +
                        "Content-length: " + content.length + "\r\n" +
                        "Connection: close\r\n\r\n";

                OutputStream os = client.getOutputStream();
                os.write(responce.getBytes());
                os.write(content);
                os.flush();
                System.out.println("Connection closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() throws IOException {
        server.close();
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
