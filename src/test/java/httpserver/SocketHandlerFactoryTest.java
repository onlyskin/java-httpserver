package httpserver;

import org.junit.Test;

import java.net.Socket;

import static org.mockito.Mockito.*;

public class SocketHandlerFactoryTest {
    @Test
    public void callsGetInputStreamOnClientSocket() throws Exception {
        SocketHandlerFactory socketHandlerFactory = new SocketHandlerFactory();
        Socket socketMock = mock(Socket.class);

        socketHandlerFactory.newSocketHandler(mock(AppConfig.class), socketMock);
        verify(socketMock).getInputStream();
        verify(socketMock).getOutputStream();
    }
}
