package httpserver.responder;

import httpserver.Request;
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
                return new Response(200, payload);
            } else {
                Path[] paths = Files.directoryContents(path);
                String result = "";
                for (Path subPath: paths) {
                    result = result + linkString(root, subPath);
                }
                return new Response(200, result.getBytes());
            }
        } else {
            return new Response(404, "".getBytes());
        }
    }
}
