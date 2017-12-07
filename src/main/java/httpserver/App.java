package httpserver;

import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;

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

        Path root = new PathExaminer().getPath(fileDirectory);
        ServerSocket serverSocket = new ServerSocket(port);

        ExecutorService pool = Executors.newFixedThreadPool(16);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            SocketHandler socketHandler = new SocketHandler(root,
                    new FileLogger(root, new FileOperator()),
                    clientSocket.getInputStream(),
                    clientSocket.getOutputStream());
            pool.execute(socketHandler);
        }
    }
}
