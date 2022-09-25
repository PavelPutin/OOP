package ru.vsu.putin_p_a.web_server.servlet_server;

import ru.vsu.putin_p_a.web_server.configuration.Configuration;
import ru.vsu.putin_p_a.web_server.http_protocol.HttpRequest;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class SocketParser implements Runnable {

    private Socket client;
    private Configuration configuration;

    public SocketParser(Socket client, Configuration configuration) {
        this.client = client;
        this.configuration = configuration;
    }

    @Override
    public void run() {
        try {
            Scanner input = new Scanner(client.getInputStream());
            String method = input.next();
            URI uri = new URI(input.next());

        } catch (IOException e) {
            System.out.println("Can't get client input or output stream");
        } catch (URISyntaxException e) {
            System.out.println("Invalid uri");
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
