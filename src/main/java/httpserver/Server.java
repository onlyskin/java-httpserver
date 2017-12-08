package httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;

public class Server {
    private final Path logPath;
    private final Path root;
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket, Path root, Path logPath) {
        this.serverSocket = serverSocket;
        this.root = root;
        this.logPath = logPath;
    }

    public void start(ExecutorService threadPool, SocketHandlerFactory socketHandlerFactory) throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            SocketHandler socketHandler = socketHandlerFactory.newSocketHandler(root, logPath, clientSocket);
            threadPool.execute(socketHandler);
        }
    }
}
