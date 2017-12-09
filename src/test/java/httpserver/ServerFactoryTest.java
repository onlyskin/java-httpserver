package httpserver;

import httpserver.file.PathExaminer;
import org.junit.Test;

import java.nio.file.Paths;

import static org.mockito.Mockito.*;

public class ServerFactoryTest {

    private final ServerSocketFactory serverSocketFactoryMock;
    private ServerFactory serverFactory;
    private String fileDirectory;
    private final PathExaminer pathExaminerMock;

    public ServerFactoryTest() {
        fileDirectory = "/example/public/";

        pathExaminerMock = mock(PathExaminer.class);
        when(pathExaminerMock.getPath(any())).thenReturn(Paths.get("example", "public"));

        serverSocketFactoryMock = mock(ServerSocketFactory.class);

        serverFactory = new ServerFactory(pathExaminerMock,
                serverSocketFactoryMock);
    }

    @Test
    public void callsGetPathOnPathExaminerWithFileDirectory() throws Exception {
        serverFactory.makeServer(5000, fileDirectory);

        verify(pathExaminerMock).getPath("/example/public/");
    }

    @Test
    public void callsPathExaminerConcatenateWithResultOfGetPathAndLogs() throws Exception {
        serverFactory.makeServer(5000, fileDirectory);

        verify(pathExaminerMock).concatenate(Paths.get("example", "public"), "logs");
    }

    @Test
    public void callsNewServerSocketOnServerSocketFactoryWithPort() throws Exception {
        serverFactory.makeServer(5000, fileDirectory);

        verify(serverSocketFactoryMock).newServerSocket(5000);
    }
}
