package httpserver;

import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;

public class SocketHandlerFactoryTest {
    @Test
    public void callsGetInputStreamOnClientSocket() throws Exception {
        SocketHandlerFactory socketHandlerFactory = new SocketHandlerFactory();
        SocketStub socketStub = new SocketStub();

        socketHandlerFactory.newSocketHandler(Paths.get("example)"),
                Paths.get("example", "logs"), socketStub);
        assertTrue(socketStub.getInputStreamCalled);
        assertTrue(socketStub.getOutputStreamCalled);
    }
}
