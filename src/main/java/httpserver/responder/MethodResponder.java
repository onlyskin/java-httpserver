package httpserver.responder;

import httpserver.Method;
import httpserver.request.Request;

public abstract class MethodResponder implements Responder {
    protected Method method;

    public Method getMethod() {
        return method;
    }

    public boolean handles(Request request) {
        return request.getMethod().equals(method);
    }
}
