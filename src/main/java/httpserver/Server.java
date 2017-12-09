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
    private boolean running;

    public Server(ServerSocket serverSocket, Path root, Path logPath) {
        this.serverSocket = serverSocket;
        this.root = root;
        this.logPath = logPath;
        this.running = false;
    }

    public void start(Executor executor, SocketHandlerFactory socketHandlerFactory) throws IOException {
        //while (true) {
            Socket clientSocket = serverSocket.accept();
            SocketHandler socketHandler = socketHandlerFactory.newSocketHandler(root, logPath, clientSocket);
            executor.execute(socketHandler);
        //}
    }

    public void exit() {
        running = false;
    }
}
