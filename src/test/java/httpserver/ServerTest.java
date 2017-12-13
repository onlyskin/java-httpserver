package httpserver;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static org.mockito.Mockito.*;

public class ServerTest {

    private final SocketHandlerFactory socketHandlerFactoryMock;
    private final ServerSocket serverSocketMock;
    private final Socket socket;
    private final AppConfig appConfigMock;
    private Server server;
    private final ExecutorService executorServiceMock;
    private final SocketHandler socketHandlerMock;
    private final Future futureMock;

    public ServerTest() throws IOException {
        socket = new Socket();
        serverSocketMock = mock(ServerSocket.class);
        when(serverSocketMock.accept()).thenReturn(socket);

        appConfigMock = mock(AppConfig.class);
        server = new Server(serverSocketMock, appConfigMock);

        socketHandlerMock = mock(SocketHandler.class);
        socketHandlerFactoryMock = mock(SocketHandlerFactory.class);
        when(socketHandlerFactoryMock.newSocketHandler(any(), any())).thenReturn(socketHandlerMock);

        executorServiceMock = mock(ExecutorService.class);
        futureMock = mock(Future.class);
        when(executorServiceMock.submit(socketHandlerMock)).thenReturn(futureMock);
    }

    @Test
    public void callsAcceptOnServerSocket() throws Exception {
        server.acceptConnection(executorServiceMock, socketHandlerFactoryMock);

        verify(serverSocketMock).accept();
    }

    @Test
    public void callsNewSocketHandlerWithCorrectArgs() throws Exception {
        server.acceptConnection(executorServiceMock, socketHandlerFactoryMock);

        verify(socketHandlerFactoryMock).newSocketHandler(appConfigMock, socket);
    }

    @Test
    public void callsExecutorSubmitWithSocketHandler() throws Exception {
        server.acceptConnection(executorServiceMock, socketHandlerFactoryMock);

        verify(executorServiceMock).submit(socketHandlerMock);
    }

    @Test
    public void callsGetOnFuture() throws Exception {
        server.acceptConnection(executorServiceMock, socketHandlerFactoryMock);

        verify(futureMock).get();
    }
}
