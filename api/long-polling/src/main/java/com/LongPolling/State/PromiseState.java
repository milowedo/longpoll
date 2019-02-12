package com.LongPolling.State;

import com.entity.Resolvable;
import org.springframework.web.context.request.async.DeferredResult;

public abstract class PromiseState {
    RequestPromise requestPromise;

    PromiseState(RequestPromise requestPromise) {
        this.requestPromise = requestPromise;
    }

    abstract public void checkForTimeout();

    abstract void update(Resolvable resolved);
}
