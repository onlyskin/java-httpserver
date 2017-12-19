package httpserver.responder;

import httpserver.AppConfig;
import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;
import httpserver.header.Header;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import java.nio.file.Path;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PutResponderTest {
    private final PutResponder putResponder;
    private final AppConfig appConfigMock;
    private final PathExaminer pathExaminerMock;
    private final FileOperator fileOperatorMock;
    private final Path rootMock;
    private final Path fullPathMock;
    private final byte[] fileContentsMock;

    public PutResponderTest() {
        pathExaminerMock = mock(PathExaminer.class);
        fileOperatorMock = mock(FileOperator.class);
        putResponder = new PutResponder(pathExaminerMock, fileOperatorMock);
        rootMock = mock(Path.class);
        appConfigMock = mock(AppConfig.class);
        when(appConfigMock.getRoot()).thenReturn(rootMock);
        fullPathMock = mock(Path.class);
        fileContentsMock = new byte[0];
    }

    @Test
    public void overwritesFileWithDataIfHandlesAndExists() throws Exception {
        String pathString = "/form";
        when(pathExaminerMock.pathExists(any())).thenReturn(true);
        when(pathExaminerMock.getFullPath(rootMock, pathString)).thenReturn(fullPathMock);
        when(fileOperatorMock.readContents(fullPathMock)).thenReturn(fileContentsMock);
        Request request = new Request("POST", pathString, new Header[0], "", "data=example");

        Response response = putResponder.respond(appConfigMock, request);

        assertEquals(200, response.getStatusCode());
        verify(fileOperatorMock).replaceContents(fullPathMock, "data=example".getBytes());
        verify(fileOperatorMock).readContents(fullPathMock);
        assertEquals(fileContentsMock, response.getPayload());
    }

    @Test
    public void returns404IfHandlesButDoesntExist() throws Exception {
        String pathString = "/form";
        when(pathExaminerMock.pathExists(any())).thenReturn(false);
        when(pathExaminerMock.getFullPath(rootMock, pathString)).thenReturn(fullPathMock);
        Request request = new Request("POST", pathString, new Header[0], "", "data=example");

        Response response = putResponder.respond(appConfigMock, request);

        assertEquals(404, response.getStatusCode());
    }

    @Test
    public void returns405IfNotHandles() throws Exception {
        Request request = new Request("POST", "/not_allowed", new Header[0], "", "data=example");

        Response response = putResponder.respond(appConfigMock, request);

        assertEquals(405, response.getStatusCode());
    }

    @Test
    public void handlesFormAndMethodOptions() throws Exception {
        assertTrue(putResponder.handles("/form"));
        assertTrue(putResponder.handles("/method_options"));
        assertFalse(putResponder.handles("/other"));
    }
}
