package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Method;
import httpserver.request.Request;
import httpserver.file.Html;
import httpserver.file.PathExaminer;
import httpserver.header.Header;
import httpserver.header.RangeHeaderValueParser;
import httpserver.response.Response;
import httpserver.route.Router;
import org.junit.Test;

import java.io.OutputStream;
import java.nio.file.Path;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HeadResponderTest {

    private final PathExaminer pathExaminerMock;
    private final Path rootMock;

    public HeadResponderTest() {
        pathExaminerMock = mock(PathExaminer.class);
        rootMock = mock(Path.class);
    }

    @Test
    public void writePayloadWritesEmptyByteArrayButContentLengthHeaderIsFull() throws Exception {
        Path fullPathMock = mock(Path.class);
        when(pathExaminerMock.getFullPath(rootMock, "/filename")).thenReturn(fullPathMock);
        when(pathExaminerMock.pathExists(fullPathMock)).thenReturn(true);
        when(pathExaminerMock.isFile(fullPathMock)).thenReturn(true);
        byte[] payloadMock = "example file contents".getBytes();
        when(pathExaminerMock.fileContents(fullPathMock)).thenReturn(payloadMock);

        AppConfig appConfigMock = mock(AppConfig.class);
        when(appConfigMock.getRoot()).thenReturn(rootMock);
        Router routerMock = mock(Router.class);
        when(routerMock.canRespond(any())).thenReturn(false);
        HeadResponder headResponder = new HeadResponder(routerMock,
                pathExaminerMock,
                mock(Html.class),
                mock(RangeHeaderValueParser.class));

        Request request = new Request(Method.HEAD, "/filename", new Header[0], "");

        Response response = headResponder.respond(appConfigMock, request);

        OutputStream outputStreamMock = mock(OutputStream.class);
        response.writePayload(outputStreamMock);
        verify(outputStreamMock).write("".getBytes());
        assertEquals("21", response.getContentLengthHeader().getValue());
    }

    @Test
    public void onlyHandlesHEAD() throws Exception {
        HeadResponder headResponder = new HeadResponder(null, null, null, null);
        Request headRequest = new Request(Method.HEAD, "", null, null);
        Request getRequest = new Request(Method.GET, "", null, null);

        assertFalse(headResponder.handles(getRequest));
        assertTrue(headResponder.handles(headRequest));
    }
}
