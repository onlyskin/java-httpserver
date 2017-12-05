package httpserver.responder;

import httpserver.NotFoundResponse;
import httpserver.Request;
import httpserver.OkResponse;
import httpserver.Response;
import httpserver.fileutils.Files;

import java.nio.file.Path;

import static httpserver.fileutils.Html.linkString;

public class GetResponder implements Responder {
    @Override
    public Response respond(Path root, Request request) {
        Path path = Files.fullPathForRequestPath(root, request.getPath());

        if (Files.pathExists(path)) {
            if (Files.isFile(path)) {
                byte[] payload = Files.fileContents(path);
                return new OkResponse(payload);
            } else {
                Path[] paths = Files.directoryContents(path);
                String result = "";
                for (Path subPath: paths) {
                    result = result + linkString(root, subPath);
                }
                return new OkResponse(result.getBytes());
            }
        } else {
            return new NotFoundResponse();
        }
    }
}
