package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Range;
import httpserver.file.Html;
import httpserver.file.PathExaminer;
import httpserver.header.Header;
import httpserver.Request;
import httpserver.header.RangeHeaderValueParser;
import httpserver.response.Response;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GetResponderTest {

    private final GetResponder getResponder;
    private final AppConfig appConfigMock;
    private final RouteMap routeMapMock;
    private final PathExaminer pathExaminerMock;
    private final Path rootMock;
    private final Html htmlMock;
    private final RangeHeaderValueParser rangeHeaderValueParserMock;

    public GetResponderTest() throws IOException {
        routeMapMock = mock(RouteMap.class);
        pathExaminerMock = mock(PathExaminer.class);

        htmlMock = mock(Html.class);
        rangeHeaderValueParserMock = mock(RangeHeaderValueParser.class);
        getResponder = new GetResponder(routeMapMock,
                pathExaminerMock,
                htmlMock,
                rangeHeaderValueParserMock);
        appConfigMock = mock(AppConfig.class);
        rootMock = mock(Path.class);
        when(appConfigMock.getRoot()).thenReturn(rootMock);
    }

    @Test
    public void returns404ForBadPath() throws Exception {
        Request request = new Request("GET",
                "nonexistentfile123", new Header[0], "");

        Response response = getResponder.respond(appConfigMock, request);

        assertEquals(404, response.getStatusCode());
        assertEquals("", new String(response.getPayload()));
    }

    @Test
    public void ifPathInRouteMapGetsResponderAndCallsRespond() throws Exception {
        Responder responderMock = mock(Responder.class);
        when(routeMapMock.hasRoute("/example_route")).thenReturn(true);
        when(routeMapMock.getResponderForRoute("/example_route")).thenReturn(responderMock);
        Request request = new Request("GET", "/example_route", new Header[0], "");

        getResponder.respond(appConfigMock, request);

        verify(routeMapMock).hasRoute("/example_route");
        verify(routeMapMock).getResponderForRoute("/example_route");
        verify(responderMock).respond(appConfigMock, request);
    }

    @Test
    public void getsFileContentsForPath() throws Exception {
        Path fullPathMock = mock(Path.class);
        when(pathExaminerMock.getFullPath(rootMock, "/filename")).thenReturn(fullPathMock);
        when(pathExaminerMock.pathExists(fullPathMock)).thenReturn(true);
        when(pathExaminerMock.isFile(fullPathMock)).thenReturn(true);
        byte[] payloadMock = new byte[0];
        when(pathExaminerMock.fileContents(fullPathMock)).thenReturn(payloadMock);
        Request request = new Request("GET", "/filename", new Header[0], "");

        Response response = getResponder.respond(appConfigMock, request);

        verify(appConfigMock).getRoot();
        verify(pathExaminerMock).getFullPath(rootMock, "/filename");
        verify(pathExaminerMock).pathExists(fullPathMock);
        verify(pathExaminerMock).isFile(fullPathMock);
        assertEquals(200, response.getStatusCode());
        assertEquals(payloadMock, response.getPayload());
        assertEquals("Content-Type", response.getHeaders()[0].getKey());
    }

    @Test
    public void respondsToRangeRequest() throws Exception {
        Path fullPathMock = mock(Path.class);
        when(pathExaminerMock.getFullPath(rootMock, "/filename")).thenReturn(fullPathMock);
        when(pathExaminerMock.pathExists(fullPathMock)).thenReturn(true);
        when(pathExaminerMock.isFile(fullPathMock)).thenReturn(true);
        byte[] payloadMock = "range test string".getBytes();
        when(pathExaminerMock.fileContents(fullPathMock)).thenReturn(payloadMock);

        String rangeHeaderValue = "bytes=3-8";
        Header[] headers = new Header[]{new Header("Range", rangeHeaderValue)};
        Range rangeMock = mock(Range.class);
        when(rangeHeaderValueParserMock.parse(rangeHeaderValue, payloadMock.length)).thenReturn(rangeMock);

        Request request = new Request("GET", "/filename", headers, "");

        Response response = getResponder.respond(appConfigMock, request);

        assertEquals(206, response.getStatusCode());
        verify(rangeHeaderValueParserMock).parse(rangeHeaderValue, payloadMock.length);
    }

    @Test
    public void getsDirContentsForPath() throws Exception {
        Path fullPathMock = mock(Path.class);
        when(pathExaminerMock.getFullPath(rootMock, "/")).thenReturn(fullPathMock);
        when(pathExaminerMock.pathExists(fullPathMock)).thenReturn(true);
        when(pathExaminerMock.isFile(fullPathMock)).thenReturn(false);
        Path path1 = mock(Path.class);
        Path path2 = mock(Path.class);
        Path[] pathArrayMock = new Path[]{path1, path2};
        when(pathExaminerMock.directoryContents(fullPathMock)).thenReturn(pathArrayMock);
        Request request = new Request("GET", "/", new Header[0], "");

        Response response = getResponder.respond(appConfigMock, request);

        verify(pathExaminerMock).isFile(fullPathMock);
        verify(pathExaminerMock).directoryContents(fullPathMock);
        verify(htmlMock).linkString(rootMock, path1);
        verify(htmlMock).linkString(rootMock, path2);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void formIsAllowed() throws Exception {
        assertTrue(getResponder.allowed("/form"));
        assertTrue(getResponder.allowed("anything"));
    }
}
