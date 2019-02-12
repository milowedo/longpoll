package com.LongPolling.State;

public class TimedOutPromise extends PromiseState {

    TimedOutPromise(RequestPromise requestPromise) {
        super(requestPromise);
    }

    @Override
    public void checkForTimeout() {
        this.setErrorResult("timedOutSORRY");
        requestPromise.changeState(new ResolvedPromise(requestPromise));
        requestPromise.checkForTimeout();
    }
}
