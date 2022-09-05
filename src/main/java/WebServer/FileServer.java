package WebServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
    private ServerSocket serverSocket;

    public FileServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void run() {
        while(true) {
            try {
                System.out.println("Ожидание клиента на порт " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                DataInputStream in = new DataInputStream(server.getInputStream());
                System.out.println(in.readUTF());

                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Спасибо за подключение к " + server.getLocalSocketAddress()
                        + "\nПока!");
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
