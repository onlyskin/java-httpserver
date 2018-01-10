package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Method;
import httpserver.request.Request;
import httpserver.response.Response;

import java.io.IOException;

public abstract class MethodResponder {
    protected Method method;

    public Method getMethod() {
        return method;
    }

    public boolean handles(Request request) {
        return request.getMethod().equals(method);
    }

    abstract boolean allows(Request request);

    abstract Response respond(AppConfig appConfig, Request request) throws IOException;
}
