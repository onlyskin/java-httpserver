package httpserver;

import org.junit.Test;

import static org.junit.Assert.*;

public class SocketServerTest {
    @Test
    public void callsServerSocketFactoryMakeServerSocketWithPort() throws Exception {
        ServerSocketStub serverSocketStub = new ServerSocketStub();
        ServerSocketFactoryMock serverSocketFactoryMock = new ServerSocketFactoryMock(serverSocketStub);
        SocketServer socketServer = new SocketServer(5000, "test", serverSocketFactoryMock);

        socketServer.run();

        assertEquals(new Integer(5000), serverSocketFactoryMock.makeServerSocketCalledWith);
    }

    @Test
    public void callsAcceptedClientSocketAccept() throws Exception {
        ServerSocketStub serverSocketStub = new ServerSocketStub();
        ServerSocketFactoryMock serverSocketFactoryMock = new ServerSocketFactoryMock(serverSocketStub);
        SocketServer socketServer = new SocketServer(5000, "test", serverSocketFactoryMock);

        socketServer.run();

        assertTrue(serverSocketStub.acceptCalled);
    }
}
