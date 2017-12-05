package httpserver;

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
        byte[] request = ("GET " + relativePath.toString() + " HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\n").getBytes();

        InputStream inputStream = new ByteArrayInputStream(request);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SocketHandler socketHandler = new SocketHandler(root);

        socketHandler.process(inputStream, outputStream);

        String expected = "HTTP/1.1 200 OK\r\nContent-Length: 35\r\n\r\nTest file contents for GET request.";
        assertEquals(expected, outputStream.toString());
    }
}
