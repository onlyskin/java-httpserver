package httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketStub extends ServerSocket {
    public boolean acceptCalled;

    public ServerSocketStub() throws IOException {
        super();
        this.acceptCalled = false;
    }

    @Override
    public Socket accept() throws IOException {
        acceptCalled = true;
        return new Socket();
    }
}
