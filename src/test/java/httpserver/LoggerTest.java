package httpserver;

import httpserver.file.FileOperator;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static httpserver.file.FileHelpers.tempDir;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Files.write;
import static java.nio.file.Files.exists;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LoggerTest {

    private final Path logPath;
    private final Logger logger;
    private final PrintStream printStreamMock;

    public LoggerTest() throws IOException {
        logPath = Paths.get(tempDir().toString(), "logs");
        printStreamMock = mock(PrintStream.class);
        logger = new Logger(logPath, new FileOperator(), printStreamMock);
    }

    @Test
    public void createsLogFileOnConstructDoesntOverwrite() throws Exception {
        assertTrue(exists(logPath));

        write(logPath, "fake log line\r\n".getBytes(),
                StandardOpenOption.APPEND);

        assertEquals("fake log line\r\n", new String(readAllBytes(logPath)));
    }

    @Test
    public void writesToLogAndReadsFromLog() throws Exception {
        logger.log("POST example");
        logger.log("HEAD example");

        String log = new String(logger.readLog());

        assertEquals("POST example\r\nHEAD example\r\n", log);
    }

    @Test
    public void printsErrorToStdErrIfCantWriteToLog() throws Exception {
        FileOperator fileOperatorMock = mock(FileOperator.class);
        doThrow(new IOException()).when(fileOperatorMock).appendToFile(any(), any());
        Logger logger = new Logger(logPath, fileOperatorMock, printStreamMock);

        logger.log("");

        verify(printStreamMock).print(any(Exception.class));
    }
}
