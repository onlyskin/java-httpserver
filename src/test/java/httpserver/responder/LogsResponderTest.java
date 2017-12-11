package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Logger;
import httpserver.Request;
import httpserver.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LogsResponderTest {
    @Test
    public void callsReadLogOnAppConfigLogger() {
        Logger loggerMock = mock(Logger.class);
        AppConfig appConfigMock = mock(AppConfig.class);
        when(appConfigMock.getLogger()).thenReturn(loggerMock);
        LogsResponder logsResponder = new LogsResponder();

        Response response = logsResponder.respond(appConfigMock, mock(Request.class));

        verify(loggerMock).readLog();
        assertEquals(200, response.getStatusCode());
    }

}
