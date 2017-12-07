package httpserver;

import httpserver.file.FileOperator;

import java.io.IOException;
import java.nio.file.Path;

public class FileLogger implements Logger {
    private final FileOperator fileOperator;
    private final Path logPath;

    public FileLogger(Path logPath, FileOperator fileOperator) {
        this.fileOperator = fileOperator;
        this.logPath = logPath;
        createFileIfDoesntExist(logPath);
    }

    @Override
    public void log(String logString){
        byte[] logBytes = (logString + "\r\n").getBytes();
        try {
            fileOperator.appendToFile(logPath, logBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFileIfDoesntExist(Path path) {
        try {
            fileOperator.createFileAtPath(path);
        } catch (IOException e) {}
    }
}