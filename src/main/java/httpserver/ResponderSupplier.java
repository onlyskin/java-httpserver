package httpserver;

import httpserver.request.Request;
import httpserver.responder.MethodResponder;

import java.util.ArrayList;
import java.util.List;

public class ResponderSupplier {
    private final MethodResponder invalidMethodResponder;
    private final List<MethodResponder> methodResponders;

    public ResponderSupplier(MethodResponder invalidMethodResponder) {
        this.invalidMethodResponder = invalidMethodResponder;
        this.methodResponders = new ArrayList<>();
    }

    public MethodResponder supplyResponder(Request request) {
        for (MethodResponder methodResponder: methodResponders) {
            if (methodResponder.handles(request)) {
                return methodResponder;
            }
        }
        return invalidMethodResponder;
    }

    public void registerResponder(MethodResponder methodResponder) {
        this.methodResponders.add(methodResponder);
    }

    public List<MethodResponder> allResponders() {
        return methodResponders;
    }
}
