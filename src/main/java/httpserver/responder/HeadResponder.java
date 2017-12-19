package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Request;
import httpserver.file.Html;
import httpserver.file.PathExaminer;
import httpserver.response.Response;

import java.io.IOException;

public class HeadResponder extends GetResponder {
    public HeadResponder(RouteMap getRouteMap,
                         PathExaminer pathExaminer,
                         Html html,
                         RangeHeaderValueParser rangeHeaderValueParser) {
        super(getRouteMap, pathExaminer, html, rangeHeaderValueParser);
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) throws IOException {
        Response response = super.respond(appConfig, request);
        response.setHeadTrue();
        return response;
    }
}
