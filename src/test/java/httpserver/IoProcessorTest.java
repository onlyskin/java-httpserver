package httpserver;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class IoProcessorTest {
    private final String tempdir;
    private final int port;

    public IoProcessorTest() {
        this.tempdir = System.getProperty("java.io.tmpdir");
        this.port = 400;
    }

    @Test
    public void writesRequestedFileContentsToOutputStreamForGET() throws Exception {
        File tempFile = File.createTempFile("temp-", "-testfile");
        tempFile.deleteOnExit();
        String fullPath = tempFile.toString();
        String relativePath = fullPath.substring(tempdir.length());
        FileOutputStream fileOutputStream = new FileOutputStream(fullPath);
        fileOutputStream.write("Test file contents for GET request.".getBytes());

        byte[] request = ("GET " + relativePath + " HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\n").getBytes();
        InputStream inputStream = new ByteArrayInputStream(request);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IoProcessor ioProcessor = new IoProcessor(port, tempdir);

        ioProcessor.process(inputStream, outputStream);

        String expected = "HTTP/1.1 200 OK\r\nContent-Length: 35\r\n\r\nTest file contents for GET request.";
        assertEquals(expected, outputStream.toString());
    }
}
