package httpserver;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketFactoryMock extends ServerSocketFactory {
    private final ServerSocketStub serverSocketStub;
    public Integer makeServerSocketCalledWith;

    public ServerSocketFactoryMock(ServerSocketStub serverSocketStub) {
        super();
        this.makeServerSocketCalledWith = null;
        this.serverSocketStub = serverSocketStub;
    }

    @Override
    public ServerSocket makeServerSocket(int port) throws IOException {
        makeServerSocketCalledWith = port;
        return serverSocketStub;
    }
}
