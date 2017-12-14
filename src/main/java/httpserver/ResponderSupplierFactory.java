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
        return new ResponderSupplier(methodResponderMap, new InvalidMethodResponder());
    }

    private RouteMap getRouteMap() {
        Map<String, Responder> routeMap = new HashMap<>();
        routeMap.put("/coffee", new CoffeeResponder());
        routeMap.put("/tea", new TeaResponder());
        routeMap.put("/logs", new LogsResponder());
        routeMap.put("/cookie", new CookieResponder());
        routeMap.put("/eat_cookie", new EatCookieResponder());
        routeMap.put("/parameters", new ParametersResponder());
        routeMap.put("/form", new FormGetResponder(new PathExaminer(), new FileOperator()));
        return new RouteMap(routeMap);
    }
}
