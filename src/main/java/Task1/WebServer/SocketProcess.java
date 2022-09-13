package Task1.WebServer;

import Task1.Validators.FileAccessForReadingValidator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocketProcess implements Runnable {
    private final Pattern REQUESTED_URL = Pattern.compile("(?<=GET\\s)/.*(?=\\sHTTP/1.1)");

    private final Socket client;
    private final Configuration configuration;

    public SocketProcess(Socket client, Configuration configuration) {
        this.client = client;
        this.configuration = configuration;
    }

    @Override
    public void run() {

        System.out.printf("Socket %s accepted...%n", client);

        Scanner input = null;
        try {
            input = new Scanner(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        try {
            OutputStream os = client.getOutputStream();
            os.write(responce.getBytes());
            os.write(content);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connection closed.");
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
