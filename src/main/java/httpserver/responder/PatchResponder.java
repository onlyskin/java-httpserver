package httpserver.responder;

import httpserver.AppConfig;
import httpserver.Hasher;
import httpserver.Method;
import httpserver.request.Request;
import httpserver.file.FileOperator;
import httpserver.file.PathExaminer;
import httpserver.response.*;

import java.io.IOException;
import java.nio.file.Path;

public class PatchResponder extends MethodResponder {
    private PathExaminer pathExaminer;
    private final FileOperator fileOperator;
    private final Hasher hasher;

    public PatchResponder(PathExaminer pathExaminer,
                          FileOperator fileOperator, Hasher hasher) {
        super.method= Method.PATCH;
        this.pathExaminer = pathExaminer;
        this.fileOperator = fileOperator;
        this.hasher = hasher;
    }

    @Override
    public Response respond(AppConfig appConfig, Request request) throws IOException {
        Path fullPath = pathExaminer.getFullPath(appConfig.getRoot(), request.getPathString());

        if (!pathExaminer.pathExists(fullPath)) {
            return new NotFoundResponse();
        }

        if (noIfMatchHeader(request)) {
            return new ConflictResponse();
        }

        if (matchingHash(fullPath, request)) {
            byte[] newFileContents = request.getBody().getBytes();
            fileOperator.replaceContents(fullPath, newFileContents);
            return new NoContentResponse(hasher.getHash(newFileContents));
        }

        return new ConflictResponse();
    }

    private boolean noIfMatchHeader(Request request) {
        return !request.hasHeader("If-Match");
    }

    private boolean matchingHash(Path fullPath, Request request) {
        byte[] fileContents = pathExaminer.fileContents(fullPath);
        String ifMatchHash = request.getHeaderValue("If-Match");
        return hasher.matches(fileContents, ifMatchHash);
    }

    public boolean allows(Request request) {
        return request.getPathString().equals("/patch-content.txt");
    }
}
