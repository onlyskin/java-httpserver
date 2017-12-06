package httpserver.fileutils;


import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.*;

public class Files {

    public static Path fullPathForRequestPath(Path root, String requestPathString) {
        return root.resolve(requestPathString.substring(1));
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

    public static byte[] fileContents(Path file) {
        try {
            return readAllBytes(file);
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public static Path[] directoryContents(Path dir) {
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = newDirectoryStream(dir)) {
            for (Path entry: stream) {
                result.add(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toArray(new Path[0]);
    }

    public static void createFileAtPath(Path path) {
        try {
            createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFileAtPath(Path path) {
        try {
            deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendToFile(Path path, byte[] contents) {
        try {
            write(path, contents, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void replaceContents(Path path, byte[] contents) {
        try {
            write(path, contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}