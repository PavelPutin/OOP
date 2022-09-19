package ru.vsu.putin_p_a.web_server.socket_server;

import ru.vsu.putin_p_a.validators.file_validators.FileAccessForReadingValidator;
import ru.vsu.putin_p_a.web_server.configuration.Configuration;

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
    private final Pattern REQUESTED_URL = Pattern.compile("(?<=GET\\s).*(?=\\sHTTP/1.1)");

    private final Socket client;
    private final Configuration configuration;

    public SocketProcess(Socket client, Configuration configuration) {
        this.client = client;
        this.configuration = configuration;
    }

    @Override
    public void run() {
        try {
            System.out.printf("Socket %s accepted...%n", client);

            Scanner input = new Scanner(client.getInputStream());
            String startingLine = input.nextLine();
            Path url = getUrl(startingLine);
            Path appName = url.getParent();

            byte[] content;
            boolean fileWasRead = false;
            if (isHelloApp(appName)) {
                Path targetName = url.getFileName();
                Path target = configuration.getRoot().resolve(targetName);

                System.out.printf("Try to send file %s%n", targetName);

                try (FileInputStream fis = new FileInputStream(target.toFile())) {
                    content = fis.readAllBytes();
                    fileWasRead = true;
                } catch (Exception e) {
                    content = "There isn't such file".getBytes();
                }
            } else {
                content = "Not supported app!".getBytes();
                System.out.println("Client requested to unexcited app");
            }

            sendResponce(content);
            if (fileWasRead) {
                System.out.println("File was sent successfully");
            } else {
                System.out.println("File was not sent");
            }

            client.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponce(byte[] content) throws IOException {
        String responce = "HTTP 200 OK\r\n" +
                "Content-length: " + content.length + "\r\n" +
                "Connection: close\r\n\r\n";

        OutputStream os = client.getOutputStream();
        os.write(responce.getBytes());
        os.write(content);
        os.flush();
    }

    private Path getUrl(String startingLine) throws IOException {
        Matcher matcher = REQUESTED_URL.matcher(startingLine);
        String result = null;
        if (matcher.find()) {
            result = startingLine.substring(matcher.start(), matcher.end());
        }
        if (result == null) {
            throw new IOException(String.format("Don't have url in %s.", startingLine));
        }
        return Paths.get(result);
    }

    private boolean isHelloApp(Path appName) {
        return appName.equals(Paths.get(configuration.getAppName()));
    }
}
