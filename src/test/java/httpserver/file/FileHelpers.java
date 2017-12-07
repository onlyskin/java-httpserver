package httpserver.file;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.createTempDirectory;
import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.write;

public class FileHelpers {
    public static Path tempDir() throws IOException {
        Path dir = createTempDirectory("dir");
        dir.toFile().deleteOnExit();
        return dir;
    }

    public static Path tempDirOptions(Path rootDir) throws IOException {
        Path dir = createTempDirectory(rootDir, "dir");
        dir.toFile().deleteOnExit();
        return dir;
    }

    public static Path tempFile() throws IOException {
        Path file = createTempFile("file", "temp");
        file.toFile().deleteOnExit();
        return file;
    }

    public static Path tempFileOptions(Path dir, String prefix) throws IOException {
        Path file = createTempFile(dir, prefix, "temp");
        file.toFile().deleteOnExit();
        return file;
    }

    public static Path fileWithContents(String contents) throws IOException {
        Path file = tempFile();
        write(file, contents.getBytes());
        return file;
    }
}
