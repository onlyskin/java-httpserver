package httpserver;

import java.net.ServerSocket;

public class ServerSocketFactorySpy extends ServerSocketFactory {
    public int newServerSocketCalledWith;

    @Override
    public ServerSocket newServerSocket(int port) {
        newServerSocketCalledWith = port;
        return null;
    }
}
