package httpserver;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerFactory {
    private final AppConfig appConfig;
    private final ServerSocketFactory serverSocketFactory;

    public ServerFactory(ServerSocketFactory serverSocketFactory,
                         AppConfig appConfig) {
        this.serverSocketFactory = serverSocketFactory;
        this.appConfig = appConfig;
    }

    public Server makeServer(int port) throws IOException {
        ServerSocket serverSocket = serverSocketFactory.newServerSocket(port);
        return new Server(serverSocket, appConfig);
    }
}
