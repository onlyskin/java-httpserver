package httpserver;

import httpserver.file.FileOperator;

import java.io.IOException;
import java.nio.file.Path;

public class Logger {
    private final FileOperator fileOperator;
    private final Path logPath;

    public Logger(Path logPath, FileOperator fileOperator) {
        this.fileOperator = fileOperator;
        this.logPath = logPath;
        createFileIfDoesntExist(logPath);
    }

    public void log(String logString) throws IOException {
        byte[] logBytes = (logString + "\r\n").getBytes();
        fileOperator.appendToFile(logPath, logBytes);
    }

    private void createFileIfDoesntExist(Path path) {
        try {
            fileOperator.createFileAtPath(path);
        } catch (IOException e) {}
    }
}
