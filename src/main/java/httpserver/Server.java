package httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Server {
    private final ServerSocket serverSocket;
    private final AppConfig appConfig;

    public Server(ServerSocket serverSocket, AppConfig appConfig) {
        this.serverSocket = serverSocket;
        this.appConfig = appConfig;
    }

    public void acceptConnection(ExecutorService executorService, SocketHandlerFactory socketHandlerFactory){
        try {
            Socket clientSocket = serverSocket.accept();
            SocketHandler socketHandler = socketHandlerFactory.newSocketHandler(appConfig, clientSocket);
            Future<?> future = executorService.submit(socketHandler);
            future.get();
            clientSocket.close();
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
