package ru.vsu.putin_p_a.web_server.socket_server;

import ru.vsu.putin_p_a.web_server.configuration.Configuration;
import ru.vsu.putin_p_a.web_server.http_protocol.ResponseStatus;

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
            boolean supportedApp = true;
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
                supportedApp = false;
                content = "Not supported app!".getBytes();
                System.out.println("Client requested to unexcited app");
            }

            if (fileWasRead && supportedApp) {
                sendResponse(content, ResponseStatus.OK);
                System.out.println("File was sent successfully");
            } else {
                sendResponse(content, ResponseStatus.NOT_FOUND);
                System.out.println("File was not sent");
            }
        } catch (IOException e) {
            System.out.println("Something went wrong with client socket input stream");
            e.printStackTrace();
        } finally {
            try {
                client.close();
                System.out.println("Connection closed.");
            } catch (IOException e) {
                System.out.println("Can't close client socket");
                e.printStackTrace();
            }
        }
    }

    private void sendResponse(byte[] content, ResponseStatus status) throws IOException {
        String response = "HTTP/1.1 " + status.getCode() + " " + status.getStatus() + "\r\n" +
                "Content-length: " + content.length + "\r\n" +
                "Connection: close\r\n\r\nHello";

        OutputStream os = client.getOutputStream();
        os.write(response.getBytes());
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
