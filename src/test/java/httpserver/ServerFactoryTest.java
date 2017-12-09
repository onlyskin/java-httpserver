package httpserver;

import httpserver.file.PathExaminerSpy;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ServerFactoryTest {

    private final Path spyGetPathResult;
    private PathExaminerSpy pathExaminerSpy;
    private ServerSocketFactorySpy serverSocketFactorySpy;
    private ServerFactory serverFactory;
    private String fileDirectory;

    public ServerFactoryTest() {
        spyGetPathResult = Paths.get("example, public");
        pathExaminerSpy = new PathExaminerSpy(spyGetPathResult);
        serverSocketFactorySpy = new ServerSocketFactorySpy();
        serverFactory = new ServerFactory(pathExaminerSpy,
                serverSocketFactorySpy);
        fileDirectory = "/example/public/";
    }

    @Test
    public void callsGetPathOnPathExaminerWithFileDirectory() throws Exception {
        serverFactory.makeServer(5000, fileDirectory);

        assertEquals(fileDirectory, pathExaminerSpy.getPathCalledWith);
    }

    @Test
    public void callsPathExaminerConcatenateWithResultOfGetPathAndLogs() throws Exception {
        serverFactory.makeServer(5000, fileDirectory);

        assertEquals(spyGetPathResult, pathExaminerSpy.concatenateCalledWith1);
        assertEquals("logs", pathExaminerSpy.concatenateCalledWith2);
    }

    @Test
    public void callsNewServerSocketOnServerSocketFactoryWithPort() throws Exception {
        serverFactory.makeServer(5000, fileDirectory);

        assertEquals(5000, serverSocketFactorySpy.newServerSocketCalledWith);
    }
}
