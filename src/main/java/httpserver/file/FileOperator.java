package httpserver.file;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static java.nio.file.Files.*;

public class FileOperator {
    public void createFileAtPath(Path path) throws IOException {
        createFile(path);
    }

    public void deleteFileAtPath(Path path) throws IOException {
        deleteIfExists(path);
    }

    public void appendToFile(Path path, byte[] contents) throws IOException {
        write(path, contents, StandardOpenOption.APPEND);
    }

    public void replaceContents(Path path, byte[] contents) throws IOException {
        write(path, contents);
    }

    public byte[] readContents(Path path) throws IOException {
        return readAllBytes(path);
    }
}
