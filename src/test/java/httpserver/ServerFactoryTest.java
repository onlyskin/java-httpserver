package httpserver;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class ServerFactoryTest {
    @Test
    public void callsNewServerSocketOnServerSocketFactoryWithPort() throws Exception {
        ServerSocketFactory serverSocketFactoryMock = mock(ServerSocketFactory.class);
        AppConfig appConfigMock = mock(AppConfig.class);
        ServerFactory serverFactory = new ServerFactory(serverSocketFactoryMock, appConfigMock);

        serverFactory.makeServer(5000);

        verify(serverSocketFactoryMock).newServerSocket(5000);
    }
}
