package httpserver;

import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.nio.file.Path;

import static httpserver.fileutils.FileHelpers.tempDir;
import static httpserver.fileutils.FileHelpers.tempFileOptions;
import static java.nio.file.Files.write;
import static org.junit.Assert.*;

public class SocketHandlerTest {

    @Test
    public void writesRequestedFileContentsToOutputStreamForGET() throws Exception {
        Path root = tempDir();
        Path file = tempFileOptions(root, "aaa");
        write(file, "Test file contents for GET request.".getBytes());

        Path relativePath = root.relativize(file);
        byte[] request = ("GET /" + relativePath.toString() + " HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\n").getBytes();

        InputStream inputStream = new ByteArrayInputStream(request);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SocketHandler socketHandler = new SocketHandler(root);

        socketHandler.process(inputStream, outputStream);

        String expected = "HTTP/1.1 200 OK\r\nContent-Length: 35\r\n\r\nTest file contents for GET request.";
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void writesRequestedDirContentsAsHtmlToOutputStreamForGET() throws Exception {
        Path root = tempDir();
        Path file1 = tempFileOptions(root, "aaa");
        Path file2 = tempFileOptions(root, "bbb");

        byte[] request = ("GET / HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\n").getBytes();

        Path relativePath1 = root.relativize(file1);
        Path relativePath2 = root.relativize(file2);

        InputStream inputStream = new ByteArrayInputStream(request);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SocketHandler socketHandler = new SocketHandler(root);

        socketHandler.process(inputStream, outputStream);

        String expectedBody = "<div><a href=\"" + relativePath1 + "\">" + relativePath1 + "</a></div>" +
                        "<div><a href=\"" + relativePath2 + "\">" + relativePath2 + "</a></div>";
        String expected = "HTTP/1.1 200 OK\r\nContent-Length: " + expectedBody.length() + "\r\n\r\n" + expectedBody;
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void returns200ForPOSTToForm() throws  Exception {
        Path root = tempDir();
        byte[] request = ("POST /form HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\nMy=Data").getBytes();
        InputStream inputStream = new ByteArrayInputStream(request);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SocketHandler socketHandler = new SocketHandler(root);

        socketHandler.process(inputStream, outputStream);

        String expected = "HTTP/1.1 200 OK\r\nContent-Length: 0\r\n\r\n";
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void returns405ForPOST() throws Exception {
        Path root = tempDir();
        byte[] request = ("POST /example HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\n").getBytes();
        InputStream inputStream = new ByteArrayInputStream(request);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SocketHandler socketHandler = new SocketHandler(root);

        socketHandler.process(inputStream, outputStream);

        String expected = "HTTP/1.1 405 Method Not Allowed\r\nContent-Length: 0\r\n\r\n";
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void returns200ForPUTToForm() throws  Exception {
        Path root = tempDir();
        byte[] request = ("PUT /form HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\nMy=Data").getBytes();
        InputStream inputStream = new ByteArrayInputStream(request);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SocketHandler socketHandler = new SocketHandler(root);

        socketHandler.process(inputStream, outputStream);

        String expected = "HTTP/1.1 200 OK\r\nContent-Length: 0\r\n\r\n";
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void returns405ForPUT() throws Exception {
        Path root = tempDir();
        byte[] request = ("PUT /example HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\n").getBytes();
        InputStream inputStream = new ByteArrayInputStream(request);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SocketHandler socketHandler = new SocketHandler(root);

        socketHandler.process(inputStream, outputStream);

        String expected = "HTTP/1.1 405 Method Not Allowed\r\nContent-Length: 0\r\n\r\n";
        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void returns405ForUnsupportedMethod() throws Exception {
        Path root = tempDir();
        byte[] request = ("XYZABC /example HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\n").getBytes();
        InputStream inputStream = new ByteArrayInputStream(request);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SocketHandler socketHandler = new SocketHandler(root);

        socketHandler.process(inputStream, outputStream);

        String expected = "HTTP/1.1 405 Method Not Allowed\r\nContent-Length: 0\r\n\r\n";
        assertEquals(expected, outputStream.toString());
    }
}
