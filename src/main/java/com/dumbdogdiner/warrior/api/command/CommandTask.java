package com.dumbdogdiner.warrior.api.command;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CommandTask<B> extends FutureTask<B> {

    protected CommandTask(Callable<B> callable) {
        super(callable);
    }

    @Override
    protected void setException(Throwable t) {
        t.printStackTrace();
    }

    /*
    @Override
    protected void done() {
        try {
            if (!super.isCancelled()) get();
        } catch (ExecutionException e) {
            e.getCause().printStackTrace();
        } catch (InterruptedException e) {
            throw new AssertionError(e);
        }
    }*/
}