package httpserver;

import httpserver.file.MimetypeChecker;

import java.nio.file.Path;

public class ContentTypeHeader extends Header {
    public ContentTypeHeader(Path path) {
        super("Content-Type", getMimeType(path));
    }

    private static String getMimeType(Path path) {
        return new MimetypeChecker().getMimeType(path);
    }
}
