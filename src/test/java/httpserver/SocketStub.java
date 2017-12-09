package httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketStub extends Socket {
    public boolean getOutputStreamCalled;
    public boolean getInputStreamCalled;

    @Override
    public InputStream getInputStream() throws IOException {
        getInputStreamCalled = true;
        return null;
    }

    public OutputStream getOutputStream() throws IOException {
        getOutputStreamCalled = true;
        return null;
    }
}
