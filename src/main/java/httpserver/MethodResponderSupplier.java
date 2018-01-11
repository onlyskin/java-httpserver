package httpserver;

import httpserver.request.Request;
import httpserver.responder.MethodResponder;

import java.util.ArrayList;
import java.util.List;

public class MethodResponderSupplier {
    private final List<MethodResponder> methodResponders;

    public MethodResponderSupplier() {
        this.methodResponders = new ArrayList<>();
    }

    public MethodResponder supplyResponder(Request request) throws Exception {
        for (MethodResponder methodResponder: methodResponders) {
            if (methodResponder.handles(request)) {
                return methodResponder;
            }
        }
        throw new Exception();
    }

    public void registerResponder(MethodResponder methodResponder) {
        this.methodResponders.add(methodResponder);
    }

    public List<MethodResponder> allResponders() {
        return methodResponders;
    }
}
