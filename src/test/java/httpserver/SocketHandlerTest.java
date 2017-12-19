package httpserver;

import httpserver.file.FileOperator;
import httpserver.responder.GeneralResponder;
import httpserver.response.Response;
import org.junit.Test;

import java.io.*;
import java.nio.file.Path;

import static httpserver.file.FileHelpers.tempDir;
import static httpserver.file.FileHelpers.tempFileOptions;
import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.write;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SocketHandlerTest {

    private final Path root;
    private final Path relativePath1;
    private final Path relativePath2;
    private final Logger logger;
    private final AppConfig appConfig;

    public SocketHandlerTest() throws IOException {
        root = tempDir();
        logger = new Logger(root, new FileOperator());
        appConfig = new AppConfig(root, logger);
        relativePath1 = root.relativize(tempFileOptions(root, "aaa", "temp"));
        relativePath2 = root.relativize(tempFileOptions(root, "bbb", "temp"));
    }

    @Test
    public void callsInjectedPartsCorrectly() throws Exception {
        AppConfig appConfigMock = mock(AppConfig.class);
        InputStream inputStreamMock = mock(InputStream.class);
        RequestParser requestParserMock = mock(RequestParser.class);
        Request requestMock = mock(Request.class);
        when(requestParserMock.parse(inputStreamMock)).thenReturn(requestMock);
        GeneralResponder generalResponderMock = mock(GeneralResponder.class);
        Response responseMock = mock(Response.class);
        when(generalResponderMock.respond(appConfigMock, requestMock)).thenReturn(responseMock);
        ResponseWriter responseWriterMock = mock(ResponseWriter.class);

        SocketHandler socketHandler = new SocketHandler(appConfigMock,
                inputStreamMock,
                requestParserMock,
                generalResponderMock,
                responseWriterMock);

        socketHandler.run();

        verify(requestParserMock).parse(inputStreamMock);
        verify(generalResponderMock).respond(appConfigMock, requestMock);
        verify(responseWriterMock).write(responseMock);
        verify(inputStreamMock).close();
    }

    @Test
    public void returns404ForMalformedRequestLine() throws Exception {
        byte[] request = ("".getBytes());

        String expected = "HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\n\r\n";
        assertEquals(expected, stringOutputForRequestBytes(request));
    }

    @Test
    public void writesRequestedFileContentsToOutputStreamForGET() throws Exception {
        Path file = tempFileOptions(root, "aaa", "temp.txt");
        write(file, "Test file contents for GET request.".getBytes());
        Path relativePath = root.relativize(file);

        byte[] request = ("GET /" + relativePath.toString() + " HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\n").getBytes();

        String expected = "HTTP/1.1 200 OK\r\nContent-Length: 35\r\nContent-Type: text/plain\r\n\r\nTest file contents for GET request.";
        assertEquals(expected, stringOutputForRequestBytes(request));
    }

    @Test
    public void writesContentTypeForGif() throws Exception {
        Path file = createTempFile(root,"aaa", ".gif");
        file.toFile().deleteOnExit();

        byte[] request = ("GET /" + root.relativize(file).toString() + " HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\n").getBytes();

        assertTrue(stringOutputForRequestBytes(request).contains("Content-Type: image/gif\r\n"));
    }

    @Test
    public void writesRequestedDirContentsAsHtmlToOutputStreamForGET() throws Exception {
        String expectedBody = "<div><a href=\"/" + relativePath1 + "\">/" + relativePath1 + "</a></div>" +
                "<div><a href=\"/" + relativePath2 + "\">/" + relativePath2 + "</a></div>";

        byte[] request = ("GET / HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\n").getBytes();

        String expected = "HTTP/1.1 200 OK\r\nContent-Length: " + expectedBody.length() + "\r\n\r\n" + expectedBody;
        assertEquals(expected, stringOutputForRequestBytes(request));
    }

    @Test
    public void returns200ForPOSTToForm() throws  Exception {
        byte[] request = ("POST /form HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\nMy=Data").getBytes();

        String expected = "HTTP/1.1 200 OK\r\nContent-Length: 0\r\n\r\n";
        assertEquals(expected, stringOutputForRequestBytes(request));
    }

    @Test
    public void returns405ForPOST() throws Exception {
        byte[] request = ("POST /example HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\n").getBytes();

        String expected = "HTTP/1.1 405 Method Not Allowed\r\nContent-Length: 0\r\n\r\n";
        assertEquals(expected, stringOutputForRequestBytes(request));
    }

    @Test
    public void returns405ForUnsupportedMethod() throws Exception {
        byte[] request = ("XYZABC /example HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\n").getBytes();

        String expected = "HTTP/1.1 405 Method Not Allowed\r\nContent-Length: 0\r\n\r\n";
        assertEquals(expected, stringOutputForRequestBytes(request));
    }

    private String stringOutputForRequestBytes(byte[] request) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(request);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        SocketHandler socketHandler = new SocketHandlerFactory().newSocketHandlerFromStreams(appConfig, inputStream, outputStream);
        socketHandler.run();

        return outputStream.toString();
    }
}
