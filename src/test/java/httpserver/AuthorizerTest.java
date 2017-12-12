package httpserver;

import httpserver.header.Header;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthorizerTest {

    private final Authorizer authorizer;

    public AuthorizerTest() {
        authorizer = new Authorizer();
    }

    @Test
    public void authorizesRequest() throws Exception {
        Header[] headers = new Header[]{new Header("Authorization", "Basic YWRtaW46aHVudGVyMg==")};
        Request request = new Request("GET", "test", headers);

        assertTrue(authorizer.authorize(request));
    }

    @Test
    public void doesntAuthorizeRequestWithInvalidCredentials() throws Exception {
        Header[] headers = new Header[]{new Header("Authorization", "Basic aaabbb")};
        Request request = new Request("GET", "test", headers);

        assertFalse(authorizer.authorize(request));
    }

    @Test
    public void doesntAuthorizeRequestWithNoAuthHeader() throws Exception {
        Request request = new Request("GET", "test", new Header[0]);

        assertFalse(authorizer.authorize(request));
    }
}
