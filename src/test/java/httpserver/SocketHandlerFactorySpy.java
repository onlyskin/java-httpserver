package httpserver;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;

public class SocketHandlerFactorySpy extends SocketHandlerFactory {
    public final SocketHandler newSocketHandlerResult;
    public Path newSocketHandlerArg1;
    public Path newSocketHandlerArg2;
    public Socket newSocketHandlerArg3;

    public SocketHandlerFactorySpy(SocketHandler newSocketHandlerResult) {
        this.newSocketHandlerResult = newSocketHandlerResult;

    }

    public SocketHandler newSocketHandler(Path root,
                                          Path logPath,
                                          Socket clientSocket) throws IOException {
        if (newSocketHandlerArg1 == null) {
            newSocketHandlerArg1 = root;
        }
        if (newSocketHandlerArg2 == null) {
            newSocketHandlerArg2 = logPath;
        }
        if (newSocketHandlerArg3 == null) {
            newSocketHandlerArg3 = clientSocket;
        }
        return newSocketHandlerResult;
    }
}
