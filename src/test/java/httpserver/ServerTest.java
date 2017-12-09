package httpserver;

import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ServerTest {

    private final Path root;
    private final Path logPath;
    private final SocketHandler socketHandler;
    private Socket acceptResultSocket;
    private ServerSocketSpy serverSocketSpy;
    private Server server;
    private SocketHandlerFactorySpy socketHandlerFactorySpy;
    private ExecutorSpy executorSpy;

    public ServerTest() throws IOException {
        acceptResultSocket = new Socket();
        serverSocketSpy = new ServerSocketSpy(acceptResultSocket);
        root = Paths.get("root");
        logPath = Paths.get("root/logs");
        server = new Server(serverSocketSpy, root, logPath);

        socketHandler = new SocketHandler(null, null, null, null);
        socketHandlerFactorySpy = new SocketHandlerFactorySpy(socketHandler);
        executorSpy = new ExecutorSpy();
    }

    @Test
    public void callsAcceptOnServerSocket() throws Exception {
        server.start(executorSpy, socketHandlerFactorySpy);
        server.exit();

        assertTrue(serverSocketSpy.acceptCalled);
    }

    @Test
    public void callsNewSocketHandlerWithCorrectArgs() throws Exception {
        server.start(executorSpy, socketHandlerFactorySpy);
        server.exit();

        assertEquals(root, socketHandlerFactorySpy.newSocketHandlerArg1);
        assertEquals(logPath, socketHandlerFactorySpy.newSocketHandlerArg2);
        assertEquals(acceptResultSocket, socketHandlerFactorySpy.newSocketHandlerArg3);
    }

    @Test
    public void callsExecutorExecuteWithSocketHandler() throws Exception {
        server.start(executorSpy, socketHandlerFactorySpy);
        server.exit();

        assertEquals(socketHandler, executorSpy.executeArg);
    }
}
