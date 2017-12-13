package httpserver;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static org.mockito.Mockito.*;

public class ServerTest {

    private final Path root;
    private final Path logPath;
    private final SocketHandlerFactory socketHandlerFactoryMock;
    private final ServerSocket serverSocketMock;
    private final Socket socket;
    private Server server;
    private final ExecutorService executorServiceMock;
    private final SocketHandler socketHandlerMock;
    private final Future futureMock;

    public ServerTest() throws IOException {
        socket = new Socket();
        serverSocketMock = mock(ServerSocket.class);
        when(serverSocketMock.accept()).thenReturn(socket);

        root = Paths.get("root");
        logPath = Paths.get("root/logs");
        server = new Server(serverSocketMock, root, logPath);

        socketHandlerMock = mock(SocketHandler.class);
        socketHandlerFactoryMock = mock(SocketHandlerFactory.class);
        when(socketHandlerFactoryMock.newSocketHandler(any(), any(), any())).thenReturn(socketHandlerMock);

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

        verify(socketHandlerFactoryMock).newSocketHandler(root, logPath, socket);
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
