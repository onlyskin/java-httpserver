package httpserver.responder;

public class RouteMap {

    private final Responder[] responders;

    public RouteMap(Responder[] routes) {
        this.responders = routes;
    }

    public boolean hasRoute(String routeString) {
        for (Responder responder: responders) {
            if (responder.handles(routeString)) {
                return true;
            }
        }
        return false;
    }

    public Responder getResponderForRoute(String routeString) {
        for (Responder responder: responders) {
            if (responder.handles(routeString)) {
                return responder;
            }
        }
        return null;
    }
}
