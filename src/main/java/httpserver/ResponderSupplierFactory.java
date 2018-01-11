package httpserver;

import httpserver.file.FileOperator;
import httpserver.file.Html;
import httpserver.file.PathExaminer;
import httpserver.header.RangeHeaderValueParser;
import httpserver.responder.*;
import httpserver.route.*;

public class ResponderSupplierFactory {
    public MethodResponderSupplier makeResponderSupplier() {
        MethodResponderSupplier methodResponderSupplier = new MethodResponderSupplier();

        PathExaminer pathExaminer = new PathExaminer();
        FileOperator fileOperator = new FileOperator();
        Router router = getRouter(pathExaminer, fileOperator);
        Html html = new Html();
        RangeHeaderValueParser rangeHeaderValueParser = new RangeHeaderValueParser();
        Hasher hasher = new Hasher();

        methodResponderSupplier.registerResponder(new GetResponder(
                router,
                pathExaminer,
                html,
                rangeHeaderValueParser));
        methodResponderSupplier.registerResponder(new HeadResponder(
                router,
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

    private Router getRouter(PathExaminer pathExaminer, FileOperator fileOperator) {
        Route[] routeList = new Route[]{
                new CoffeeRoute(),
                new TeaRoute(),
                new LogsRoute(),
                new CookieRoute(),
                new EatCookieRoute(),
                new ParametersRoute(),
                new FormGetRoute(pathExaminer, fileOperator),
                new RedirectRoute(),
        };
        return new Router(routeList);
    }
}
