package httpserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWrapper {
    private final Path file;

    public FileWrapper(String path) {
        this.file = Paths.get(path);
    }

    public byte[] contents() {
        try {
            return Files.readAllBytes(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
