package WebServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
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
            System.out.println("Server started...");
            Socket client = server.accept();

            Scanner input = new Scanner(client.getInputStream());
            String startingLine = input.nextLine();

            String url = getUrl(startingLine);
            String appName = getAppNameFromURL(url);
            String targetName = getFileNameFromURL(url);



            client.close();
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
