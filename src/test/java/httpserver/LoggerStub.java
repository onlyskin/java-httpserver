package httpserver;

public class LoggerStub implements Logger {
    public String logCalledWith;

    public LoggerStub() {
        this.logCalledWith = null;
    }

    @Override
    public void log(String logString) {
        logCalledWith = logString;
        return;
    }
}
