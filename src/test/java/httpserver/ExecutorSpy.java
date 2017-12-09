package httpserver;

import java.util.concurrent.Executor;

public class ExecutorSpy implements Executor {
    public Runnable executeArg;

    @Override
    public void execute(Runnable command) {
        executeArg = command;
        return;
    }
}
