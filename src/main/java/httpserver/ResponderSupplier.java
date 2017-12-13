package httpserver;

import httpserver.responder.Responder;

import java.util.Map;

public class ResponderSupplier {
    private final Map<Method, Responder> methodResponderMap;
    private final Responder invalidMethodResponder;

    public ResponderSupplier(Map<Method, Responder> methodResponderMap,
                             Responder invalidMethodResponder) {
        this.methodResponderMap = methodResponderMap;
        this.invalidMethodResponder = invalidMethodResponder;
    }

    public Responder responderForMethodString(String methodString) {
        Method method;
        try {
            method = Method.valueOf(methodString);
        } catch (IllegalArgumentException e) {
            return invalidMethodResponder;
        }
        return methodResponderMap.get(method);
    }
}
