package httpserver;

import httpserver.file.FileOperator;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;

public class SocketHandlerFactory {
    public SocketHandler newSocketHandler(Path root, Path logPath, Socket clientSocket) throws IOException {
        return new SocketHandler(root,
                new Logger(logPath, new FileOperator()),
                clientSocket.getInputStream(),
                clientSocket.getOutputStream());
    }
}
