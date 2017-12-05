package httpserver.fileutils;

import java.nio.file.Path;

import static httpserver.fileutils.Files.isDir;

public class Uri {
    public static String hrefString(Path root, Path fullFilePath) {
        Path diff = root.relativize(fullFilePath);
        if (isDir(fullFilePath)) {
            return diff.toString() + "/";
        }
        return diff.toString();
    }
}
