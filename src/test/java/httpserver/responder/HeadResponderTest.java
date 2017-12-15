package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.file.Html;
import httpserver.file.PathExaminer;
import httpserver.header.Header;
import httpserver.response.Response;
import org.junit.Test;

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
    public void getPayloadReturnsEmptyByteArrayButContentLengthHeaderIsFull() throws Exception {
        Path fullPathMock = mock(Path.class);
        when(pathExaminerMock.getFullPath(rootMock, "/filename")).thenReturn(fullPathMock);
        when(pathExaminerMock.pathExists(fullPathMock)).thenReturn(true);
        when(pathExaminerMock.isFile(fullPathMock)).thenReturn(true);
        byte[] payloadMock = "example file contents".getBytes();
        when(pathExaminerMock.fileContents(fullPathMock)).thenReturn(payloadMock);

        AppConfig appConfigMock = mock(AppConfig.class);
        when(appConfigMock.getRoot()).thenReturn(rootMock);
        RouteMap routeMapMock = mock(RouteMap.class);
        when(routeMapMock.hasRoute(any())).thenReturn(false);
        HeadResponder headResponder = new HeadResponder(routeMapMock, pathExaminerMock, mock(Html.class));

        Request request = new Request("HEAD", "/filename", new Header[0], "");

        Response response = headResponder.respond(appConfigMock, request);

        assertEquals("", new String(response.getPayload()));
        assertEquals("21", response.getContentLengthHeader().getValue());
    }
}
