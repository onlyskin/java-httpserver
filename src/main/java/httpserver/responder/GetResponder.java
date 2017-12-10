package httpserver.responder;

import httpserver.ContentTypeHeader;
import httpserver.Header;
import httpserver.response.NotFoundResponse;
import httpserver.Request;
import httpserver.response.OkResponse;
import httpserver.response.Response;
import httpserver.file.PathExaminer;

import java.nio.file.Path;

import static httpserver.file.Html.linkString;

public class GetResponder implements Responder {

    private final PathExaminer pathExaminer;

    public GetResponder() {
        pathExaminer = new PathExaminer();
    }

    @Override
    public Response respond(Path root, Request request) {
        Path path = pathExaminer.fullPathForRequestPath(root, request.getPath());

        if (pathExaminer.pathExists(path)) {
            if (pathExaminer.isFile(path)) {
                return responseForFile(path);
            } else {
                return responseForDir(root, path);
            }
        } else {
            return new NotFoundResponse();
        }
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
}
