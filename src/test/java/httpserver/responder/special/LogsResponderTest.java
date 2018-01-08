package httpserver.responder.special;

import httpserver.AppConfig;
import httpserver.header.Header;
import httpserver.Logger;
import httpserver.request.Request;
import httpserver.response.Response;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LogsResponderTest {

    private final Logger loggerMock;
    private final AppConfig appConfigMock;
    private final LogsResponder logsResponder;

    public LogsResponderTest() {
        logsResponder = new LogsResponder();

        loggerMock = mock(Logger.class);
        appConfigMock = mock(AppConfig.class);
        when(appConfigMock.getLogger()).thenReturn(loggerMock);
    }

    @Test
    public void callsReadLogOnAppConfigLogger() throws Exception {
        Header[] headers = new Header[]{new Header("Authorization", "Basic YWRtaW46aHVudGVyMg==")};
        Request request = new Request("GET", "test", headers, "");

        Response response = logsResponder.respond(appConfigMock, request);

        verify(loggerMock).readLog();
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void returnsUnauthorizedResponseWhenIncorrectAuthHeader() throws Exception {
        Header[] headers = new Header[]{new Header("Authorization", "Basic YWRtaW46aHVudGVyMg==")};
        Request request = new Request("GET", "test", headers, "");
        when(loggerMock.readLog()).thenThrow(new IOException());

        Response response = logsResponder.respond(appConfigMock, request);

        assertEquals(500, response.getStatusCode());
    }

    @Test
    public void returns500ResponseWhenLogErrors() throws Exception {
        Request request = new Request("GET", "test", new Header[0], "");
    }
    @Test

    public void allowsLogs() throws Exception {
        assertTrue(logsResponder.allows("/logs"));
        assertFalse(logsResponder.allows("/other"));
    }
}
