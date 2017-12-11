package httpserver;

import httpserver.file.FileOperator;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static httpserver.file.FileHelpers.tempDir;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.write;
import static java.nio.file.Files.exists;
import static org.junit.Assert.*;

public class LoggerTest {

    private final Path logPath;
    private final Logger logger;

    public LoggerTest() throws IOException {
        logPath = Paths.get(tempDir().toString(), "logs");
        logger = new Logger(logPath, new FileOperator());
    }
    @Test
    public void createsLogFileOnConstructDoesntOverwrite() throws Exception {
        assertTrue(exists(logPath));

        write(logPath, "fake log line\r\n".getBytes(),
                StandardOpenOption.APPEND);

        new Logger(logPath, new FileOperator());

        assertEquals("fake log line\r\n", new String(readAllBytes(logPath)));
    }

    @Test
    public void writesToLogAndReadsFromLog() throws Exception {
        logger.log("POST example");
        logger.log("HEAD example");

        String log = new String(logger.readLog());

        assertEquals("POST example\r\nHEAD example\r\n", log);
    }
}
