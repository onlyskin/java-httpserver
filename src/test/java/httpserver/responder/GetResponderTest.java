package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Method;
import httpserver.Range;
import httpserver.file.Html;
import httpserver.file.PathExaminer;
import httpserver.header.Header;
import httpserver.request.Request;
import httpserver.header.RangeHeaderValueParser;
import httpserver.response.Response;
import httpserver.route.Route;
import httpserver.route.Router;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GetResponderTest {

    private final GetResponder getResponder;
    private final AppConfig appConfigMock;
    private final Router routerMock;
    private final PathExaminer pathExaminerMock;
    private final Path rootMock;
    private final Html htmlMock;
    private final RangeHeaderValueParser rangeHeaderValueParserMock;

    public GetResponderTest() throws IOException {
        routerMock = mock(Router.class);
        pathExaminerMock = mock(PathExaminer.class);

        htmlMock = mock(Html.class);
        rangeHeaderValueParserMock = mock(RangeHeaderValueParser.class);
        getResponder = new GetResponder(routerMock,
                pathExaminerMock,
                htmlMock,
                rangeHeaderValueParserMock);
        appConfigMock = mock(AppConfig.class);
        rootMock = mock(Path.class);
        when(appConfigMock.getRoot()).thenReturn(rootMock);
    }

    @Test
    public void returns404ForBadPath() throws Exception {
        Request request = new Request(Method.GET,
                "nonexistentfile123", new Header[0], "");

        Response response = getResponder.respond(appConfigMock, request);

        assertEquals(404, response.getStatusCode());
        OutputStream outputStreamMock = mock(OutputStream.class);
        response.writePayload(outputStreamMock);
        verify(outputStreamMock).write("".getBytes());
    }

    @Test
    public void ifRouterCanRespondThenReturnsRouterRespond() throws Exception {
        Response responseMock = mock(Response.class);
        when(routerMock.canRespond(any())).thenReturn(true);
        when(routerMock.respond(any(), any())).thenReturn(responseMock);
        Request requestMock = mock(Request.class);

        Response actual = getResponder.respond(appConfigMock, requestMock);

        verify(routerMock).canRespond(requestMock);
        verify(routerMock).respond(appConfigMock, requestMock);
        assertEquals(responseMock, actual);
    }

    @Test
    public void getsFileContentsForPath() throws Exception {
        Path fullPathMock = mock(Path.class);
        when(pathExaminerMock.getFullPath(rootMock, "/filename")).thenReturn(fullPathMock);
        when(pathExaminerMock.pathExists(fullPathMock)).thenReturn(true);
        when(pathExaminerMock.isFile(fullPathMock)).thenReturn(true);
        byte[] payloadMock = new byte[0];
        when(pathExaminerMock.fileContents(fullPathMock)).thenReturn(payloadMock);
        Request request = new Request(Method.GET, "/filename", new Header[0], "");

        Response response = getResponder.respond(appConfigMock, request);

        verify(appConfigMock).getRoot();
        verify(pathExaminerMock).getFullPath(rootMock, "/filename");
        verify(pathExaminerMock).pathExists(fullPathMock);
        verify(pathExaminerMock).isFile(fullPathMock);
        assertEquals(200, response.getStatusCode());
        assertEquals("Content-Type", response.getHeaders()[0].getKey());

        OutputStream outputStreamMock = mock(OutputStream.class);
        response.writePayload(outputStreamMock);
        verify(outputStreamMock).write(payloadMock);
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

        Request request = new Request(Method.GET, "/filename", headers, "");

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
        Request request = new Request(Method.GET, "/", new Header[0], "");

        Response response = getResponder.respond(appConfigMock, request);

        verify(pathExaminerMock).isFile(fullPathMock);
        verify(pathExaminerMock).directoryContents(fullPathMock);
        verify(htmlMock).linkString(rootMock, path1);
        verify(htmlMock).linkString(rootMock, path2);
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void allowsForm() throws Exception {
        assertTrue(getResponder.allows(new Request(null, "/form", null, null)));
        assertTrue(getResponder.allows(new Request(null, "anything", null, null)));
    }

    @Test
    public void handlesGET() throws Exception {
        Request getRequest = new Request(Method.GET, "", null, null);
        assertTrue(getResponder.handles(getRequest));
    }
}
