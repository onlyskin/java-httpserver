package httpserver;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.*;

public class ServerTest {

    private final SocketHandlerFactory socketHandlerFactoryMock;
    private final ServerSocket serverSocketMock;
    private final Socket socket;
    private final AppConfig appConfigMock;
    private final Server server;
    private final ExecutorService executorServiceMock;
    private final SocketHandler socketHandlerMock;

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
    public void acceptConnectionLogsExceptionIfThereIsAnIoException() throws Exception {
        when(serverSocketMock.accept()).thenThrow(new IOException());
        Logger loggerMock = mock(Logger.class);
        when(appConfigMock.getLogger()).thenReturn(loggerMock);

        server.acceptConnection(executorServiceMock, socketHandlerFactoryMock);

        verify(loggerMock).log(any());
    }
}
