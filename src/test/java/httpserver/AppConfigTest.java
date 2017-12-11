package httpserver;

import org.junit.Test;

import java.nio.file.Path;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AppConfigTest {
    @Test
    public void itHasLoggerAndRoot() {
        Logger loggerMock = mock(Logger.class);
        Path rootMock = mock(Path.class);
        AppConfig appConfig = new AppConfig(loggerMock, rootMock);

        assertEquals(appConfig.getLogger(), loggerMock);
        assertEquals(appConfig.getRoot(), rootMock);
    }
}
