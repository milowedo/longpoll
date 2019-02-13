package com.LongPolling.State;

import com.LongPolling.Resolvable;

public abstract class PromiseState {
    RequestPromise requestPromise;

    PromiseState(RequestPromise requestPromise) {
        this.requestPromise = requestPromise;
    }

    abstract public void checkForTimeout();

    abstract void update(Resolvable resolved);
}
