package com.LongPolling.State;

import com.LongPolling.Resolvable;

public class STATETimedOutPromise extends STATEPromise {

    STATETimedOutPromise(RequestPromise requestPromise) {
        super(requestPromise);
    }

    @Override
    public void checkForTimeout() {
        requestPromise.setErrorResult("timedOutSORRY");
        requestPromise.changeState(new STATEResolvedPromise(requestPromise));
        requestPromise.checkForTimeout();
    }

    @Override
    void update(Resolvable resolved) {
        requestPromise.setErrorResult("timedOutSORRY");
        requestPromise.changeState(new STATEResolvedPromise(requestPromise));
        requestPromise.checkForTimeout();
    }
}
