package httpserver.responder;

import httpserver.response.NotFoundResponse;
import httpserver.Request;
import httpserver.response.OkResponse;
import httpserver.response.Response;
import httpserver.fileutils.Files;

import java.nio.file.Path;

import static httpserver.fileutils.Html.linkString;

public class GetResponder implements Responder {
    @Override
    public Response respond(Path root, Request request) {
        Path path = Files.fullPathForRequestPath(root, request.getPath());

        if (Files.pathExists(path)) {
            if (Files.isFile(path)) {
                return responseForFile(path);
            } else {
                return responseForDir(root, path);
            }
        } else {
            return new NotFoundResponse();
        }
    }

    private Response responseForDir(Path root, Path path) {
        Path[] paths = Files.directoryContents(path);
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
        byte[] payload = Files.fileContents(path);
        return new OkResponse(payload);
    }

}
