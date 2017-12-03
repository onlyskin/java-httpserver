package httpserver;

import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.*;

public class ServerTest {
    private final String tempdir;

    public ServerTest() {
        this.tempdir = System.getProperty("java.io.tmpdir");
    }

    @Ignore
    @Test
    public void writesRequestedFileContentsToSocketOutForGET() throws Exception {
        File tempFile = File.createTempFile("temp-", "-testfile");
        tempFile.deleteOnExit();
        String fullPath = tempFile.toString();
        String relativePath = fullPath.substring(tempdir.length());

        FileOutputStream fileOutputStream = new FileOutputStream(fullPath);
        fileOutputStream.write("Test file contents for GET request.".getBytes());

        Server server = new Server(5000, tempdir);
        server.run();

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                BufferedReader bufferedReader = null;
                try (Socket clientSocket = new Socket("127.0.0.1", 5000)) {
                    OutputStream outputStream = clientSocket.getOutputStream();
                    String requestString = "GET " + relativePath + " HTTP/1.1\r\nHost: 127.0.0.1:5000\r\n\r\n";
                    outputStream.write(requestString.getBytes());
                    outputStream.flush();

                    bufferedReader = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    String line = bufferedReader.readLine();
                    assertEquals("HTTP/1.1 200 OK", line);
                    line = bufferedReader.readLine();
                    assertEquals("Content-Length: 35", line);
                    line = bufferedReader.readLine();
                    assertEquals("", line);
                    line = bufferedReader.readLine();
                    assertEquals("Test file contents for GET request.", line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
