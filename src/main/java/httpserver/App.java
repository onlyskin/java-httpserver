package httpserver;

import httpserver.fileutils.Files;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[1]);
        String fileDirectory = args[3];
        Path root = Files.getPath(fileDirectory);

        ExecutorService pool = Executors.newFixedThreadPool(16);
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            SocketHandler socketHandler = new SocketHandler(root,
                    clientSocket.getInputStream(), clientSocket.getOutputStream());
            pool.execute(socketHandler);
        }
    }
}
