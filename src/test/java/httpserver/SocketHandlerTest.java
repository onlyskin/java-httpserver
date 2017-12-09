package httpserver;

import httpserver.file.FileOperator;
import org.junit.Test;

import java.io.*;
import java.nio.file.Path;

import static httpserver.file.FileHelpers.tempDir;
import static httpserver.file.FileHelpers.tempFileOptions;
import static java.nio.file.Files.write;
import static org.junit.Assert.*;

public class SocketHandlerTest {

    private final Path root;
    private final Path relativePath1;
    private final Path relativePath2;

    public SocketHandlerTest() throws IOException {
        root = tempDir();
        relativePath1 = root.relativize(tempFileOptions(root, "aaa"));
        relativePath2 = root.relativize(tempFileOptions(root, "bbb"));
    }

    @Test
    public void returns404ForMalformedRequestLine() throws Exception {
        byte[] request = ("".getBytes());

        String expected = "HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\n\r\n";
        assertEquals(expected, stringOutputForRequestBytes(request));
    }

    @Test
    public void writesRequestedFileContentsToOutputStreamForGET() throws Exception {
        Path file = tempFileOptions(root, "aaa");
        write(file, "Test file contents for GET request.".getBytes());
        Path relativePath = root.relativize(file);

        byte[] request = ("GET /" + relativePath.toString() + " HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\n").getBytes();

        String expected = "HTTP/1.1 200 OK\r\nContent-Length: 35\r\n\r\nTest file contents for GET request.";
        assertEquals(expected, stringOutputForRequestBytes(request));
    }

    @Test
    public void writesRequestedDirContentsAsHtmlToOutputStreamForGET() throws Exception {
        String expectedBody = "<div><a href=\"" + relativePath1 + "\">" + relativePath1 + "</a></div>" +
                "<div><a href=\"" + relativePath2 + "\">" + relativePath2 + "</a></div>";

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
    public void returns200ForPUTToForm() throws  Exception {
        byte[] request = ("PUT /form HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\nMy=Data").getBytes();

        String expected = "HTTP/1.1 200 OK\r\nContent-Length: 0\r\n\r\n";
        assertEquals(expected, stringOutputForRequestBytes(request));
    }

    @Test
    public void returns405ForPUT() throws Exception {
        byte[] request = ("PUT /example HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\n").getBytes();

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
        Logger logger = new Logger(root, new FileOperator());

        SocketHandler socketHandler = new SocketHandler(root, logger, inputStream, outputStream);
        socketHandler.run();

        return outputStream.toString();
    }
}
