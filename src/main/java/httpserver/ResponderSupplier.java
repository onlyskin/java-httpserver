package httpserver;

import httpserver.responder.Responder;

import java.util.HashMap;
import java.util.Map;

public class ResponderSupplier {
    private final Responder invalidMethodResponder;
    private final Map<Method, Responder> methodResponderMap;

    public ResponderSupplier(Responder invalidMethodResponder) {
        this.invalidMethodResponder = invalidMethodResponder;
        this.methodResponderMap = new HashMap<>();
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

    public void registerResponder(Method method, Responder responder) {
        this.methodResponderMap.put(method, responder);
    }
}
