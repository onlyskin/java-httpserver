package httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[1]);
        String fileDirectory = args[3];
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                SocketHandler socketHandler = new SocketHandler(fileDirectory);
                socketHandler.process(clientSocket.getInputStream(), clientSocket.getOutputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
