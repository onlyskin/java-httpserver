package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Range;
import httpserver.file.Html;
import httpserver.header.ContentTypeHeader;
import httpserver.header.RangeHeaderValueParser;
import httpserver.response.NotFoundResponse;
import httpserver.Request;
import httpserver.response.OkResponse;
import httpserver.response.PartialContentResponse;
import httpserver.response.Response;
import httpserver.file.PathExaminer;

import java.io.IOException;
import java.nio.file.Path;

public class GetResponder implements Responder {

    private final PathExaminer pathExaminer;
    private final RouteMap specialCaseRouteMap;
    private final Html html;
    private RangeHeaderValueParser rangeHeaderValueParser;

    public GetResponder(RouteMap getRouteMap,
                        PathExaminer pathExaminer,
                        Html html,
                        RangeHeaderValueParser rangeHeaderValueParser) {
        this.pathExaminer = pathExaminer;
        this.specialCaseRouteMap = getRouteMap;
        this.html = html;
        this.rangeHeaderValueParser = rangeHeaderValueParser;
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) throws IOException {
        String requestPathString = request.getPathString();

        if (specialCaseRouteMap.hasRoute(requestPathString)) {
            return specialCaseRouteMap.getResponderForRoute(requestPathString).respond(appConfig, request);
        }

        Path root = appConfig.getRoot();
        Path path = pathExaminer.getFullPath(root, requestPathString);

        if (pathExaminer.pathExists(path)) {
            if (pathExaminer.isFile(path)) {
                return responseForFile(path, request);
            } else {
                return responseForDir(root, path);
            }
        }

        return new NotFoundResponse();
    }

    private Response responseForDir(Path root, Path path) {
        Path[] paths = pathExaminer.directoryContents(path);
        String result = htmlLinksForContents(root, paths);
        return new OkResponse(result.getBytes());
    }

    private String htmlLinksForContents(Path root, Path[] paths) {
        String result = "";
        for (Path subPath: paths) {
            result = result + html.linkString(root, subPath);
        }
        return result;
    }

    private Response responseForFile(Path path, Request request) {
        byte[] payload = pathExaminer.fileContents(path);

        Response response = null;
        if(request.hasHeader("Range")) {
            String rangeHeaderValue = request.getHeaderValue("Range");
            Range range = rangeHeaderValueParser.parse(rangeHeaderValue, payload.length);
            response = new PartialContentResponse(range, payload);
        } else {
            response = new OkResponse(payload);
        }

        response.setHeader(new ContentTypeHeader(path));
        return response;
    }

    public boolean handles(String s) {
        return true;
    }
}
