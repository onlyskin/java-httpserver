package httpserver;

import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;
import httpserver.responder.*;

import java.util.HashMap;
import java.util.Map;

public class ResponderSupplierFactory {
    public ResponderSupplier makeResponderSupplier() {
        Map<Method, Responder> methodResponderMap = new HashMap<>();
        methodResponderMap.put(Method.GET, new GetResponder());
        methodResponderMap.put(Method.POST, new PostResponder());
        methodResponderMap.put(Method.PUT, new PutResponder());
        return new ResponderSupplier(methodResponderMap, new InvalidMethodResponder());
    }
}
