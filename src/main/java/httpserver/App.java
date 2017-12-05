package httpserver;

import httpserver.fileutils.Files;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;

public class App {
    public static void main(String[] args) {
        int port = Integer.parseInt(args[1]);
        String fileDirectory = args[3];
        Path root = Files.getPath(fileDirectory);
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                SocketHandler socketHandler = new SocketHandler(root);
                socketHandler.process(clientSocket.getInputStream(), clientSocket.getOutputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
