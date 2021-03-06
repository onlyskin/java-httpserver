package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Method;
import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;
import httpserver.header.Header;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import java.io.OutputStream;
import java.nio.file.Path;

import static org.junit.Assert.*;
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
    public void overwritesFileWithDataIfAllowsAndExists() throws Exception {
        String pathString = "/form";
        when(pathExaminerMock.pathExists(any())).thenReturn(true);
        when(pathExaminerMock.getFullPath(rootMock, pathString)).thenReturn(fullPathMock);
        when(fileOperatorMock.readContents(fullPathMock)).thenReturn(fileContentsMock);
        Request request = new Request(Method.POST, pathString, new Header[0], "", "data=example");

        Response response = postResponder.respond(appConfigMock, request);

        assertEquals(200, response.getStatusCode());
        verify(fileOperatorMock).replaceContents(fullPathMock, "data=example".getBytes());
        verify(fileOperatorMock).readContents(fullPathMock);
        OutputStream outputStreamMock = mock(OutputStream.class);
        response.writePayload(outputStreamMock);
        verify(outputStreamMock).write(fileContentsMock);
    }

    @Test
    public void createsFileWithDataIfAllowsButDoesntExist() throws Exception {
        String pathString = "/form";
        when(pathExaminerMock.pathExists(any())).thenReturn(false);
        when(pathExaminerMock.getFullPath(rootMock, pathString)).thenReturn(fullPathMock);
        when(fileOperatorMock.readContents(fullPathMock)).thenReturn(fileContentsMock);
        Request request = new Request(Method.POST, pathString, new Header[0], "", "data=example");

        Response response = postResponder.respond(appConfigMock, request);

        assertEquals(200, response.getStatusCode());
        verify(fileOperatorMock).createFileAtPath(fullPathMock);
        verify(fileOperatorMock).replaceContents(fullPathMock, "data=example".getBytes());
        verify(fileOperatorMock).readContents(fullPathMock);
        OutputStream outputStreamMock = mock(OutputStream.class);
        response.writePayload(outputStreamMock);
        verify(outputStreamMock).write(fileContentsMock);
    }

    @Test
    public void allowsFormAndMethodOptions() throws Exception {
        assertTrue(postResponder.allows(new Request(null, "/form", null, null)));
        assertTrue(postResponder.allows(new Request(null, "/method_options", null, null)));
        assertFalse(postResponder.allows(new Request(null, "/other", null, null)));
    }

    @Test
    public void handlesPUT() throws Exception {
        Request postRequest = new Request(Method.POST, "", null, null);
        assertTrue(postResponder.handles(postRequest));
    }
}
