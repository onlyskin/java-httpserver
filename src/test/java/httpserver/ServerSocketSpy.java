package httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketSpy extends ServerSocket {
    private final Socket socket;
    public boolean acceptCalled;

    public ServerSocketSpy(Socket socket) throws IOException {
        this.acceptCalled = false;
        this.socket = socket;
    }

    @Override
    public Socket accept() throws IOException {
        acceptCalled = true;
        return socket;
    }
}
