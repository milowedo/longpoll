package com.LongPolling.State;

import com.LongPolling.Overseer;
import com.LongPolling.Resolvable;

public class STATEReadyPromise extends STATEPromise {

    public STATEReadyPromise(RequestPromise requestPromise) {
        super(requestPromise);
    }

    @Override
    void update(Resolvable resolved) {
        requestPromise.setResult(resolved);
        requestPromise.changeState(new STATEResolvedPromise(requestPromise));
        requestPromise.update(null);
    }

    @Override
    public void checkForTimeout() {
        if ((System.currentTimeMillis() - this.requestPromise.getPromiseSession().getCreationTime() + Overseer.getRefreshTime()) > Overseer.getTIMEOUT()) {
            requestPromise.changeState(new STATETimedOutPromise(requestPromise));
            this.requestPromise.checkForTimeout();
        }
    }
}
