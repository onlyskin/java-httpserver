package httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Server {
    private final Path logPath;
    private final Path root;
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket, Path root, Path logPath) {
        this.serverSocket = serverSocket;
        this.root = root;
        this.logPath = logPath;
    }

    public void acceptConnection(ExecutorService executorService, SocketHandlerFactory socketHandlerFactory){
        try (Socket clientSocket = serverSocket.accept()) {
            SocketHandler socketHandler = socketHandlerFactory.newSocketHandler(root, logPath, clientSocket);
            Future<?> future = executorService.submit(socketHandler);
            future.get();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
