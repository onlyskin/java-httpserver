package httpserver.responder;

import java.util.Map;

public class RouteMap {
    private final Map<String, Responder> routeStringToResponder;

    public RouteMap(Map<String, Responder> routes) {
        this.routeStringToResponder = routes;
    }

    public boolean contains(String routeString) {
        return routeStringToResponder.containsKey(routeString);
    }

    public Responder getResponder(String routeString) {
        return routeStringToResponder.get(routeString);
    }
}
