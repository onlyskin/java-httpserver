package httpserver;

import httpserver.file.FileOperator;
import httpserver.file.Html;
import httpserver.file.PathExaminer;
import httpserver.responder.*;

public class ResponderSupplierFactory {
    public ResponderSupplier makeResponderSupplier() {
        ResponderSupplier responderSupplier = new ResponderSupplier(new InvalidMethodResponder());

        responderSupplier.registerResponder(Method.GET, new GetResponder(
                getRouteMap(),
                new PathExaminer(),
                new Html()));
        responderSupplier.registerResponder(Method.HEAD, new HeadResponder(
                getRouteMap(),
                new PathExaminer(),
                new Html()));
        responderSupplier.registerResponder(Method.POST, new PostResponder(
                new PathExaminer(),
                new FileOperator()));
        responderSupplier.registerResponder(Method.PUT, new PutResponder(
                new PathExaminer(),
                new FileOperator()));
        responderSupplier.registerResponder(Method.PATCH, new PatchResponder(
                new PathExaminer(),
                new FileOperator(),
                new Hasher()));
        responderSupplier.registerResponder(Method.DELETE, new DeleteResponder(
                new PathExaminer(),
                new FileOperator()));
        responderSupplier.registerResponder(Method.OPTIONS, new OptionsResponder(responderSupplier));

        return responderSupplier;
    }

    private RouteMap getRouteMap() {
        Responder[] responderList = new Responder[]{
                new CoffeeResponder(),
                new TeaResponder(),
                new LogsResponder(),
                new CookieResponder(),
                new EatCookieResponder(),
                new ParametersResponder(),
                new FormGetResponder(new PathExaminer(), new FileOperator()),
                new RedirectResponder(),
        };
        return new RouteMap(responderList);
    }
}
