package httpserver.responder;

import httpserver.request.Request;

public abstract class MethodResponder implements Responder {
    protected String methodString;

    public String getMethodString() {
        return methodString;
    }

    public boolean handles(Request request) {
        return request.getMethodString().equals(this.methodString);
    }
}
