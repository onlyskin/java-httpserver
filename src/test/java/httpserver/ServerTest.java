package httpserver;

import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executor;

import static org.mockito.Mockito.*;

public class ServerTest {

    private final Path root;
    private final Path logPath;
    private final SocketHandlerFactory socketHandlerFactoryMock;
    private final ServerSocket serverSocketMock;
    private final Socket socket;
    private Server server;
    private final Executor executorMock;
    private final SocketHandler socketHandlerMock;

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

        executorMock = mock(Executor.class);
    }

    @Test
    public void callsAcceptOnServerSocket() throws Exception {
        server.acceptConnection(executorMock, socketHandlerFactoryMock);

        verify(serverSocketMock).accept();
    }

    @Test
    public void callsNewSocketHandlerWithCorrectArgs() throws Exception {
        server.acceptConnection(executorMock, socketHandlerFactoryMock);

        verify(socketHandlerFactoryMock).newSocketHandler(root, logPath, socket);
    }

    @Test
    public void callsExecutorExecuteWithSocketHandler() throws Exception {
        server.acceptConnection(executorMock, socketHandlerFactoryMock);

        verify(executorMock).execute(socketHandlerMock);
    }
}
