package httpserver;

import org.junit.Test;

import java.net.Socket;
import java.nio.file.Paths;

import static org.mockito.Mockito.*;

public class SocketHandlerFactoryTest {
    @Test
    public void callsGetInputStreamOnClientSocket() throws Exception {
        SocketHandlerFactory socketHandlerFactory = new SocketHandlerFactory();
        Socket socketMock = mock(Socket.class);

        socketHandlerFactory.newSocketHandler(Paths.get("example)"),
                Paths.get("example", "logs"), socketMock);
        verify(socketMock).getInputStream();
        verify(socketMock).getOutputStream();
    }
}
