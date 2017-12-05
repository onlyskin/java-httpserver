package httpserver.fileutils;


import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.exists;
import static java.nio.file.Files.isDirectory;

public class Files {

    public static Path getRequestPath(Path root, String requestPathString) {
        return root.resolve(requestPathString);
    }

    public static Path getPath(String input) {
        String[] parts;
        parts = input.split("/");
        Path path = Paths.get("/", parts);
        return path;
    }

    public static boolean pathExists(Path path) {
        return exists(path);
    }

    public static boolean isDir(Path path) {
        return exists(path) && isDirectory(path);
    }

    public static boolean isFile(Path path) {
        return exists(path) && !isDirectory(path);
    }
}