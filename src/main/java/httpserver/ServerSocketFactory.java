package httpserver;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketFactory {
    public ServerSocket newServerSocket(int port) throws IOException {
        return new ServerSocket(port);
    }
}
