package httpserver.route;

import httpserver.AppConfig;
import httpserver.Method;
import httpserver.request.Request;
import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;
import httpserver.header.Header;
import httpserver.response.Response;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FormGetRouteTest {
    private final FormGetRoute formGetResponder;
    private final PathExaminer pathExaminerMock;
    private final FileOperator fileOperatorMock;
    private final Path rootMock;
    private final Path fullPathMock;
    private final byte[] fileContentsMock;
    private final AppConfig appConfigMock;
    private final String pathString;

    public FormGetRouteTest() throws IOException {
        rootMock = mock(Path.class);
        pathString = "/form";
        fileContentsMock = "file contents mock".getBytes();
        fullPathMock = mock(Path.class);
        pathExaminerMock = mock(PathExaminer.class);
        when(pathExaminerMock.getFullPath(rootMock, pathString)).thenReturn(fullPathMock);
        fileOperatorMock = mock(FileOperator.class);
        when(fileOperatorMock.readContents(fullPathMock)).thenReturn(fileContentsMock);
        appConfigMock = mock(AppConfig.class);
        when(appConfigMock.getRoot()).thenReturn(rootMock);
        formGetResponder = new FormGetRoute(pathExaminerMock, fileOperatorMock);
    }
    
    @Test
    public void returnsOkResponseWithPayload() throws Exception {
        when(pathExaminerMock.pathExists(any())).thenReturn(true);
        Request request = new Request(Method.GET, pathString, new Header[0], "");

        Response response = formGetResponder.respond(appConfigMock, request);

        verify(pathExaminerMock).pathExists(fullPathMock);
        assertEquals(200, response.getStatusCode());

        OutputStream outputStreamMock = mock(OutputStream.class);
        response.writePayload(outputStreamMock);
        verify(outputStreamMock).write(fileContentsMock);
    }

    @Test
    public void createsFormFileIfDoesntExist() throws Exception {
        when(pathExaminerMock.pathExists(any())).thenReturn(false);
        Request request = new Request(Method.GET, pathString, new Header[0], "");

        Response response = formGetResponder.respond(appConfigMock, request);

        verify(fileOperatorMock).createFileAtPath(fullPathMock);
    }

    @Test
    public void allowsForm() throws Exception {
        assertTrue(formGetResponder.allows(new Request(null, "/form", null, null)));
        assertFalse(formGetResponder.allows(new Request(null, "/other", null, null)));
    }
}
