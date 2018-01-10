package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Method;
import httpserver.request.Request;
import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;
import httpserver.header.Header;
import httpserver.response.Response;
import org.junit.Test;

import java.nio.file.Path;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DeleteResponderTest {
    private final Path rootMock;
    private final AppConfig appConfigMock;
    private final Path fullPathMock;
    private PathExaminer pathExaminerMock;
    private FileOperator fileOperatorMock;
    private DeleteResponder deleteResponder;

    public DeleteResponderTest() {
        pathExaminerMock = mock(PathExaminer.class);
        fileOperatorMock = mock(FileOperator.class);
        deleteResponder = new DeleteResponder(pathExaminerMock, fileOperatorMock);
        rootMock = mock(Path.class);
        appConfigMock = mock(AppConfig.class);
        when(appConfigMock.getRoot()).thenReturn(rootMock);
        fullPathMock = mock(Path.class);
    }

    @Test
    public void returns200DeletesFileWhenExists() throws Exception {
        String pathString = "/form";
        when(pathExaminerMock.pathExists(any())).thenReturn(true);
        when(pathExaminerMock.getFullPath(rootMock, pathString)).thenReturn(fullPathMock);
        Request request = new Request(Method.DELETE, pathString, new Header[0], "", "data=example");

        Response response = deleteResponder.respond(appConfigMock, request);

        verify(fileOperatorMock).deleteFileAtPath(fullPathMock);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void returns404WhenAllowedButDoesntExist() throws Exception {
        String pathString = "/form";
        when(pathExaminerMock.pathExists(any())).thenReturn(false);
        when(pathExaminerMock.getFullPath(rootMock, pathString)).thenReturn(fullPathMock);
        Request request = new Request(Method.DELETE, pathString, new Header[0], "", "data=example");

        Response response = deleteResponder.respond(appConfigMock, request);

        assertEquals(404, response.getStatusCode());
    }

    @Test
    public void returns405WhenNotAllowed() throws Exception {
        Request request = new Request(Method.DELETE, "/not_allowed", new Header[0], "");

        Response response = deleteResponder.respond(appConfigMock, request);

        assertEquals(405, response.getStatusCode());
    }

    @Test
    public void allowsForm() throws Exception {
        assertTrue(deleteResponder.allows("/form"));
        assertFalse(deleteResponder.allows("/other"));
    }

    @Test
    public void handlesDELETE() throws Exception {
        Request deleteRequest = new Request(Method.DELETE, "", null, null);
        assertTrue(deleteResponder.handles(deleteRequest));
    }
}
