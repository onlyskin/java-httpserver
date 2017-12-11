package httpserver.responder;

import httpserver.ContentTypeHeader;
import httpserver.Header;
import httpserver.response.FourEighteenResponse;
import httpserver.response.NotFoundResponse;
import httpserver.Request;
import httpserver.response.OkResponse;
import httpserver.response.Response;
import httpserver.file.PathExaminer;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static httpserver.file.Html.linkString;

public class GetResponder implements Responder {

    private final PathExaminer pathExaminer;
    private final Map<String, Response> specialCaseRouteMap;

    public GetResponder() {
        pathExaminer = new PathExaminer();
        specialCaseRouteMap = getRouteMap();
    }

    @Override
    public Response respond(Path root, Request request) {
        String requestPathString = request.getPathString();

        if (specialCaseRouteMap.containsKey(requestPathString)) {
            return specialCaseRouteMap.get(requestPathString);
        }

        Path path = pathExaminer.getFullPath(root, requestPathString);
        if (pathExaminer.pathExists(path)) {
            if (pathExaminer.isFile(path)) {
                return responseForFile(path);
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
            result = result + linkString(root, subPath);
        }
        return result;
    }

    private Response responseForFile(Path path) {
        byte[] payload = pathExaminer.fileContents(path);
        Header[] headers = new Header[]{new ContentTypeHeader(path)};
        return new OkResponse(payload, headers);
    }

    private Map<String, Response> getRouteMap() {
        Map<String, Response> routeMap = new HashMap<>();
        routeMap.put("/coffee", new FourEighteenResponse());
        routeMap.put("/tea", new OkResponse("".getBytes()));
        return routeMap;
    }
}
