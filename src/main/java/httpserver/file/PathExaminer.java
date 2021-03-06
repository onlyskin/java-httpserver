package httpserver.file;


import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.*;

public class PathExaminer {

    public Path getFullPath(Path root, String requestPath) {
        return root.resolve(requestPath.substring(1));
    }

    public Path getPath(String input) {
        String[] parts;
        parts = input.split("/");
        return Paths.get("/", parts);
    }

    public boolean pathExists(Path path) {
        return exists(path);
    }

    public boolean isDir(Path path) {
        return exists(path) && isDirectory(path);
    }

    public boolean isFile(Path path) {
        return exists(path) && !isDirectory(path);
    }

    public byte[] fileContents(Path file) throws IOException {
        return readAllBytes(file);
    }

    public Path[] directoryContents(Path dir) throws IOException {
        List<Path> result = new ArrayList<>();
        DirectoryStream<Path> stream = newDirectoryStream(dir);
        for (Path entry: stream) {
            result.add(entry);
        }
        return result.toArray(new Path[0]);
    }

    public Path concatenate(Path root, String suffix) {
        return Paths.get(root.toString(), suffix);
    }
}