package httpserver.responder;

import httpserver.AppConfig;
import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;
import httpserver.header.Header;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import java.nio.file.Path;
import java.util.Arrays;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

public class PostResponderTest {

    private final PostResponder postResponder;
    private final AppConfig appConfigMock;
    private final PathExaminer pathExaminerMock;
    private final FileOperator fileOperatorMock;
    private final Path rootMock;
    private final Path fullPathMock;
    private final byte[] fileContentsMock;

    public PostResponderTest() {
        pathExaminerMock = mock(PathExaminer.class);
        fileOperatorMock = mock(FileOperator.class);
        postResponder = new PostResponder(pathExaminerMock, fileOperatorMock);
        rootMock = mock(Path.class);
        appConfigMock = mock(AppConfig.class);
        when(appConfigMock.getRoot()).thenReturn(rootMock);
        fullPathMock = mock(Path.class);
        fileContentsMock = new byte[0];
    }

    @Test
    public void overwritesFileWithDataIfAllowedExists() throws Exception {
        String pathString = "/form";
        when(pathExaminerMock.pathExists(any())).thenReturn(true);
        when(pathExaminerMock.getFullPath(rootMock, pathString)).thenReturn(fullPathMock);
        when(fileOperatorMock.readContents(fullPathMock)).thenReturn(fileContentsMock);
        Request request = new Request("POST", pathString, new Header[0], "", "data=example");

        Response response = postResponder.respond(appConfigMock, request);

        assertEquals(200, response.getStatusCode());
        verify(fileOperatorMock).replaceContents(fullPathMock, "data=example".getBytes());
        verify(fileOperatorMock).readContents(fullPathMock);
        assertEquals(fileContentsMock, response.getPayload());
    }

    @Test
    public void createsFileWithDataIfAllowedAndDoesntExist() throws Exception {
        String pathString = "/form";
        when(pathExaminerMock.pathExists(any())).thenReturn(false);
        when(pathExaminerMock.getFullPath(rootMock, pathString)).thenReturn(fullPathMock);
        when(fileOperatorMock.readContents(fullPathMock)).thenReturn(fileContentsMock);
        Request request = new Request("POST", pathString, new Header[0], "", "data=example");

        Response response = postResponder.respond(appConfigMock, request);

        assertEquals(200, response.getStatusCode());
        verify(fileOperatorMock).createFileAtPath(fullPathMock);
        verify(fileOperatorMock).replaceContents(fullPathMock, "data=example".getBytes());
        verify(fileOperatorMock).readContents(fullPathMock);
        assertEquals(fileContentsMock, response.getPayload());
    }

    @Test
    public void returns405IfNotAllowed() throws Exception {
        Request request = new Request("POST", "/not_allowed", new Header[0], "", "data=example");

        Response response = postResponder.respond(appConfigMock, request);

        assertEquals(405, response.getStatusCode());
    }
}
