package httpserver.file;


import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.*;

public class PathExaminer {

    public Path getFullPath(Path root, String requestPathString) {
        return root.resolve(requestPathString.substring(1));
    }

    public Path getPath(String input) {
        String[] parts;
        parts = input.split("/");
        Path path = Paths.get("/", parts);
        return path;
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

    public byte[] fileContents(Path file) {
        try {
            return readAllBytes(file);
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public Path[] directoryContents(Path dir) {
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = newDirectoryStream(dir)) {
            for (Path entry: stream) {
                result.add(entry);
            }
        } catch (IOException e) {
            System.out.println("Couldn't retrieve directory contents for directory at " + dir.toString());
        }
        return result.toArray(new Path[0]);
    }

    public Path concatenate(Path root, String suffix) {
        return Paths.get(root.toString(), suffix);
    }
}