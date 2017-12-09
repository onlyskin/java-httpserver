package httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.concurrent.Executor;

public class Server {
    private final Path logPath;
    private final Path root;
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket, Path root, Path logPath) {
        this.serverSocket = serverSocket;
        this.root = root;
        this.logPath = logPath;
    }

    public void acceptConnection(Executor executor, SocketHandlerFactory socketHandlerFactory) throws IOException {
        Socket clientSocket = serverSocket.accept();
        SocketHandler socketHandler = socketHandlerFactory.newSocketHandler(root, logPath, clientSocket);
        executor.execute(socketHandler);
    }
}
