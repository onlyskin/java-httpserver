package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Hasher;
import httpserver.Method;
import httpserver.request.Request;
import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;
import httpserver.header.Header;
import httpserver.response.Response;
import org.junit.Test;

import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PatchResponderTest {
    private final PathExaminer pathExaminerMock;
    private final FileOperator fileOperatorMock;
    private final AppConfig appConfigMock;
    private final Path rootMock;
    private final Path fullPathMock;
    private final byte[] fileContentsMock;
    private final Hasher hasherMock;
    private PatchResponder patchResponder;

    public PatchResponderTest() {
        pathExaminerMock = mock(PathExaminer.class);
        fileOperatorMock = mock(FileOperator.class);
        hasherMock = mock(Hasher.class);
        this.patchResponder = new PatchResponder(pathExaminerMock, fileOperatorMock, hasherMock);
        rootMock = mock(Path.class);
        appConfigMock = mock(AppConfig.class);
        when(appConfigMock.getRoot()).thenReturn(rootMock);
        fullPathMock = mock(Path.class);
        fileContentsMock = new byte[0];
    }

    @Test
    public void returns409IfNoIfMatchHeader() throws Exception {
        String pathString = "/patch-content.txt";
        when(pathExaminerMock.pathExists(any())).thenReturn(true);
        when(pathExaminerMock.getFullPath(rootMock, pathString)).thenReturn(fullPathMock);
        when(hasherMock.matches(any(), any())).thenReturn(true);
        Header[] headers = new Header[0];
        Request request = new Request(Method.PATCH, pathString, headers, "", "patch contents");

        Response response = patchResponder.respond(appConfigMock, request);

        assertEquals(409, response.getStatusCode());
    }

    @Test
    public void callsHasherReturns204AndUpdatesFileWithRequestBodyIfHashesMatch() throws Exception {
        String pathString = "/patch-content.txt";
        when(pathExaminerMock.pathExists(any())).thenReturn(true);
        when(pathExaminerMock.getFullPath(rootMock, pathString)).thenReturn(fullPathMock);
        when(pathExaminerMock.fileContents(fullPathMock)).thenReturn("mock text".getBytes());
        when(hasherMock.matches(any(), any())).thenReturn(true);
        when(hasherMock.getHash(any())).thenReturn("hash code");
        String hashMock = "bfc13a";
        Header[] headers = new Header[]{new Header("If-Match", hashMock)};
        Request request = new Request(Method.PATCH, pathString, headers, "", "patch contents");

        Response response = patchResponder.respond(appConfigMock, request);

        verify(pathExaminerMock).fileContents(fullPathMock);
        verify(hasherMock).matches("mock text".getBytes(), hashMock);
        verify(fileOperatorMock).replaceContents(fullPathMock, "patch contents".getBytes());
        assertEquals(204, response.getStatusCode());
        Header[] expected = new Header[]{new Header("ETag", "hash code")};
        assertTrue(Arrays.equals(expected, response.getHeaders()));
    }

    @Test
    public void returns409IfHashesDontMatch() throws Exception {
        String pathString = "/patch-content.txt";
        when(pathExaminerMock.pathExists(any())).thenReturn(true);
        when(pathExaminerMock.getFullPath(rootMock, pathString)).thenReturn(fullPathMock);
        when(pathExaminerMock.fileContents(fullPathMock)).thenReturn("mock text".getBytes());
        when(hasherMock.matches(any(), any())).thenReturn(false);
        String hashMock = "bfc13a";
        Header[] headers = new Header[]{new Header("If-Match", hashMock)};
        Request request = new Request(Method.PATCH, pathString, headers, "", "patch contents");

        Response response = patchResponder.respond(appConfigMock, request);

        verify(hasherMock).matches("mock text".getBytes(), hashMock);
        assertEquals(409, response.getStatusCode());
    }

    @Test
    public void returns404IfAllowsButDoesntExist() throws Exception {
        String pathString = "/patch-content.txt";
        when(pathExaminerMock.pathExists(any())).thenReturn(false);
        when(pathExaminerMock.getFullPath(rootMock, pathString)).thenReturn(fullPathMock);
        Request request = new Request(Method.PATCH, pathString, new Header[0], "", "data=example");

        Response response = patchResponder.respond(appConfigMock, request);

        assertEquals(404, response.getStatusCode());
    }

    @Test
    public void returns405IfNotAllows() throws Exception {
        Request request = new Request(Method.PATCH, "/not_allowed", new Header[0], "", "data=example");

        Response response = patchResponder.respond(appConfigMock, request);

        assertEquals(405, response.getStatusCode());
    }

    @Test
    public void allowsPatchContentTxt() throws Exception {
        assertTrue(patchResponder.allows("/patch-content.txt"));
        assertFalse(patchResponder.allows("/other"));
    }

    @Test
    public void handlesPATCH() throws Exception {
        Request patchRequest = new Request(Method.PATCH, "", null, null);
        assertTrue(patchResponder.handles(patchRequest));
    }
}
