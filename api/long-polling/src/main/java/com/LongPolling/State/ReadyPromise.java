package com.LongPolling.State;

import com.LongPolling.Overseer;
import com.LongPolling.Resolvable;

public class ReadyPromise extends PromiseState {

    public ReadyPromise(RequestPromise requestPromise) {
        super(requestPromise);
    }

    @Override
    void update(Resolvable resolved) {
        requestPromise.setResult(resolved);
        requestPromise.changeState(new ResolvedPromise(requestPromise));
        requestPromise.update(null);
    }

    @Override
    public void checkForTimeout() {
        if ((System.currentTimeMillis() - this.requestPromise.getPromiseSession().getCreationTime() + Overseer.getRefreshTime()) > Overseer.getTIMEOUT()) {
            requestPromise.changeState(new TimedOutPromise(requestPromise));
            this.requestPromise.checkForTimeout();
        }
    }
}
