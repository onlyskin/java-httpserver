package httpserver;

import httpserver.file.FileOperator;
import httpserver.file.Html;
import httpserver.file.PathExaminer;
import httpserver.header.RangeHeaderValueParser;
import httpserver.responder.*;
import httpserver.responder.special.*;

public class ResponderSupplierFactory {
    public MethodResponderSupplier makeResponderSupplier() {
        MethodResponderSupplier methodResponderSupplier = new MethodResponderSupplier();

        PathExaminer pathExaminer = new PathExaminer();
        FileOperator fileOperator = new FileOperator();
        RouteMap routeMap = getRouteMap(pathExaminer, fileOperator);
        Html html = new Html();
        RangeHeaderValueParser rangeHeaderValueParser = new RangeHeaderValueParser();
        Hasher hasher = new Hasher();

        methodResponderSupplier.registerResponder(new GetResponder(
                routeMap,
                pathExaminer,
                html,
                rangeHeaderValueParser));
        methodResponderSupplier.registerResponder(new HeadResponder(
                routeMap,
                pathExaminer,
                html,
                rangeHeaderValueParser));
        methodResponderSupplier.registerResponder(new PostResponder(
                pathExaminer,
                fileOperator));
        methodResponderSupplier.registerResponder(new PutResponder(
                pathExaminer,
                fileOperator));
        methodResponderSupplier.registerResponder(new PatchResponder(
                pathExaminer,
                fileOperator,
                hasher));
        methodResponderSupplier.registerResponder(new DeleteResponder(
                pathExaminer,
                fileOperator));
        methodResponderSupplier.registerResponder(new OptionsResponder(methodResponderSupplier));

        return methodResponderSupplier;
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
