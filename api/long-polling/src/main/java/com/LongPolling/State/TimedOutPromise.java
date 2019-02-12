package com.LongPolling.State;

import com.entity.Resolvable;

public class TimedOutPromise extends PromiseState {

    TimedOutPromise(RequestPromise requestPromise) {
        super(requestPromise);
    }

    @Override
    public void checkForTimeout() {
        requestPromise.setErrorResult("timedOutSORRY");
        requestPromise.changeState(new ResolvedPromise(requestPromise));
        requestPromise.checkForTimeout();
    }

    @Override
    void update(Resolvable resolved) {
        requestPromise.setErrorResult("timedOutSORRY");
        requestPromise.changeState(new ResolvedPromise(requestPromise));
        requestPromise.checkForTimeout();
    }
}
