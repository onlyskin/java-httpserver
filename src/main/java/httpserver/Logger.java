package httpserver;

import httpserver.file.FileOperator;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;

public class Logger {
    private final FileOperator fileOperator;
    private final Path logPath;
    private final PrintStream errorStream;

    public Logger(Path logPath, FileOperator fileOperator, PrintStream errorStream) throws IOException {
        this.fileOperator = fileOperator;
        this.logPath = logPath;
        this.errorStream = errorStream;
        createFileIfDoesntExist(logPath);
    }

    public void log(String logString) {
        byte[] logBytes = (logString + "\r\n").getBytes();
        try {
            fileOperator.appendToFile(logPath, logBytes);
        } catch (IOException e) {
            errorStream.print(e);
        }
    }

    private void createFileIfDoesntExist(Path path) {
        try {
            fileOperator.createFileAtPath(path);
        } catch (IOException e) { }
    }

    public byte[] readLog() throws IOException {
        return fileOperator.readContents(logPath);
    }
}
