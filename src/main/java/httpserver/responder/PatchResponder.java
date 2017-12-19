package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Hasher;
import httpserver.Request;
import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;
import httpserver.response.*;

import java.io.IOException;
import java.nio.file.Path;

public class PatchResponder implements Responder {
    private PathExaminer pathExaminer;
    private final FileOperator fileOperator;
    private final Hasher hasher;

    public PatchResponder(PathExaminer pathExaminer,
                          FileOperator fileOperator, Hasher hasher) {
        this.pathExaminer = pathExaminer;
        this.fileOperator = fileOperator;
        this.hasher = hasher;
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) throws IOException {
        if (allowed(request.getPathString())) {
            Path fullPath = pathExaminer.getFullPath(appConfig.getRoot(), request.getPathString());
            if (pathExaminer.pathExists(fullPath)) {
                if (noIfMatchHeader(request)) {
                    return new ConflictResponse();
                }
                if (matchingHash(fullPath, request)) {
                    byte[] newFileContents = request.getBody().getBytes();
                    fileOperator.replaceContents(fullPath, newFileContents);
                    return new NoContentResponse(hasher.getHash(newFileContents));
                }
                return new ConflictResponse();
            } else {
                return new NotFoundResponse();
            }
        }
        return new MethodNotAllowedResponse();
    }

    private boolean noIfMatchHeader(Request request) {
        return !request.hasHeader("If-Match");
    }

    private boolean matchingHash(Path fullPath, Request request) {
        byte[] fileContents = pathExaminer.fileContents(fullPath);
        String ifMatchHash = request.getHeaderValue("If-Match");
        return hasher.matches(fileContents, ifMatchHash);
    }

    public boolean allowed(String pathString) {
        return pathString.equals("/patch-content.txt");
    }
}
