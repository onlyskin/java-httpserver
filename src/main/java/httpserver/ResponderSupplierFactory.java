package httpserver;

import httpserver.file.FileOperator;
import httpserver.file.Html;
import httpserver.file.PathExaminer;
import httpserver.responder.*;

import java.util.HashMap;
import java.util.Map;

public class ResponderSupplierFactory {
    public ResponderSupplier makeResponderSupplier() {
        Map<Method, Responder> methodResponderMap = new HashMap<>();
        methodResponderMap.put(Method.GET, new GetResponder(
                getRouteMap(),
                new PathExaminer(),
                new Html()));
        methodResponderMap.put(Method.POST, new PostResponder(
                new PathExaminer(),
                new FileOperator()));
        methodResponderMap.put(Method.PUT, new PutResponder(
                new PathExaminer(),
                new FileOperator()));
        methodResponderMap.put(Method.DELETE, new DeleteResponder(
                new PathExaminer(),
                new FileOperator()));
        //methodResponderMap.put(Method.OPTIONS, new OptionsResponder(this));
        return new ResponderSupplier(methodResponderMap, new InvalidMethodResponder());
    }

    private RouteMap getRouteMap() {
        Responder[] responderList = new Responder[]{
                new CoffeeResponder(),
                new TeaResponder(),
                new LogsResponder(),
                new CookieResponder(),
                new EatCookieResponder(),
                new ParametersResponder(),
                new FormGetResponder(new PathExaminer(), new FileOperator())
        };
        return new RouteMap(responderList);
    }
}
