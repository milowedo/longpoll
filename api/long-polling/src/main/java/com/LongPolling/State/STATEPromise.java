package com.LongPolling.State;

import com.LongPolling.Resolvable;

public abstract class STATEPromise {
    RequestPromise requestPromise;

    STATEPromise(RequestPromise requestPromise) {
        this.requestPromise = requestPromise;
    }

    abstract public void checkForTimeout();

    abstract void update(Resolvable resolved);
}
