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

public class LoggerTest {
    @Test
    public void createsLogFileOnConstructDoesntOverwrite() throws Exception {
        Path logPath = Paths.get(tempDir().toString(), "logs");

        new Logger(logPath, new FileOperator());

        assertTrue(exists(logPath));

        write(logPath, "fake log line\r\n".getBytes(),
                StandardOpenOption.APPEND);

        new Logger(logPath, new FileOperator());

        assertEquals("fake log line\r\n", new String(readAllBytes(logPath)));
    }

    @Test
    public void onLogCallsFileOperatorAppendWithPathAndPayload() throws Exception {
        Path logPath = Paths.get(tempDir().toString(), "logs");
        Logger logger = new Logger(logPath, new FileOperator());

        logger.log("POST example");
        logger.log("HEAD example");

        assertEquals("POST example\r\nHEAD example\r\n", new String(readAllBytes(logPath)));
    }

    @Test
    public void readsLog() throws Exception {
        Path logPath = Paths.get(tempDir().toString(), "logs");
        Logger logger = new Logger(logPath, new FileOperator());

        logger.log("POST example");
        logger.log("HEAD example");

        assertEquals("POST example\r\nHEAD example\r\n", new String(logger.readLog()));
    }
}
