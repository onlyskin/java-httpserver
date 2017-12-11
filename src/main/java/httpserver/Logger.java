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

    public byte[] readLog() {
        try {
            return fileOperator.readContents(logPath);
        } catch (IOException e) {}
        return null;
    }
}
