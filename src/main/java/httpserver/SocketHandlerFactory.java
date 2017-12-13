package httpserver;

import java.io.IOException;
import java.net.Socket;

public class SocketHandlerFactory {
    public SocketHandler newSocketHandler(AppConfig appConfig, Socket clientSocket) throws IOException {
        return new SocketHandler(appConfig,
                clientSocket.getInputStream(),
                clientSocket.getOutputStream());
    }
}
