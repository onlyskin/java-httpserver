package httpserver.file;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static java.nio.file.Files.createFile;
import static java.nio.file.Files.deleteIfExists;
import static java.nio.file.Files.write;

public class FileOperator {
    public void createFileAtPath(Path path) {
        try {
            createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFileAtPath(Path path) {
        try {
            deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendToFile(Path path, byte[] contents) {
        try {
            write(path, contents, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replaceContents(Path path, byte[] contents) {
        try {
            write(path, contents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
