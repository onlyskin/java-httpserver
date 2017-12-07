package httpserver;

import httpserver.file.FileOperator;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static httpserver.file.FileHelpers.tempDir;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.write;
import static java.nio.file.Files.exists;
import static org.junit.Assert.*;

public class FileLoggerTest {
    @Test
    public void createsLogFileOnConstructDoesntOverwrite() throws Exception {
        Path logPath = Paths.get(tempDir().toString(), "logs");

        new FileLogger(logPath, new FileOperator());

        assertTrue(exists(logPath));

        write(logPath, "fake log line\r\n".getBytes(),
                StandardOpenOption.APPEND);

        new FileLogger(logPath, new FileOperator());

        assertEquals("fake log line\r\n", new String(readAllBytes(logPath)));
    }

    @Test
    public void onLogCallsFileOperatorAppendWithPathAndPayload() throws Exception {
        Path logPath = Paths.get(tempDir().toString(), "logs");
        FileLogger fileLogger = new FileLogger(logPath, new FileOperator());

        fileLogger.log("POST example");
        fileLogger.log("HEAD example");

        assertEquals("POST example\r\nHEAD example\r\n", new String(readAllBytes(logPath)));
    }
}
