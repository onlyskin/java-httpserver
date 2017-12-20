package httpserver;

import httpserver.file.FileOperator;
import httpserver.file.Html;
import httpserver.file.PathExaminer;
import httpserver.header.RangeHeaderValueParser;
import httpserver.responder.*;
import httpserver.responder.special.*;

public class ResponderSupplierFactory {
    public ResponderSupplier makeResponderSupplier() {
        ResponderSupplier responderSupplier = new ResponderSupplier(new InvalidMethodResponder());

        PathExaminer pathExaminer = new PathExaminer();
        FileOperator fileOperator = new FileOperator();
        RouteMap routeMap = getRouteMap(pathExaminer, fileOperator);
        Html html = new Html();
        RangeHeaderValueParser rangeHeaderValueParser = new RangeHeaderValueParser();
        Hasher hasher = new Hasher();

        responderSupplier.registerResponder(Method.GET, new GetResponder(
                routeMap,
                pathExaminer,
                html,
                rangeHeaderValueParser));
        responderSupplier.registerResponder(Method.HEAD, new HeadResponder(
                routeMap,
                pathExaminer,
                html,
                rangeHeaderValueParser));
        responderSupplier.registerResponder(Method.POST, new PostResponder(
                pathExaminer,
                fileOperator));
        responderSupplier.registerResponder(Method.PUT, new PutResponder(
                pathExaminer,
                fileOperator));
        responderSupplier.registerResponder(Method.PATCH, new PatchResponder(
                pathExaminer,
                fileOperator,
                hasher));
        responderSupplier.registerResponder(Method.DELETE, new DeleteResponder(
                pathExaminer,
                fileOperator));
        responderSupplier.registerResponder(Method.OPTIONS, new OptionsResponder(responderSupplier));

        return responderSupplier;
    }

    private RouteMap getRouteMap(PathExaminer pathExaminer, FileOperator fileOperator) {
        Responder[] responderList = new Responder[]{
                new CoffeeResponder(),
                new TeaResponder(),
                new LogsResponder(),
                new CookieResponder(),
                new EatCookieResponder(),
                new ParametersResponder(),
                new FormGetResponder(pathExaminer, fileOperator),
                new RedirectResponder(),
        };
        return new RouteMap(responderList);
    }
}
