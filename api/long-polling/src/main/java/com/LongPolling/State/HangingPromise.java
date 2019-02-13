package com.LongPolling.State;

import com.LongPolling.Overseer;
import com.LongPolling.Resolvable;

public class HangingPromise extends PromiseState {

    HangingPromise(RequestPromise requestPromise) {
        super(requestPromise);
    }

    @Override
    void update(Resolvable resolved) {
        if(resolved.getClass().getName().equals(requestPromise.getExpectedReturnType())) {
            requestPromise.changeState(new ReadyPromise(requestPromise));
            requestPromise.update(resolved);
        }
    }

    @Override
    public void checkForTimeout() {
        if(this.requestPromise.getPromiseSession() == null) requestPromise.changeState(new ResolvedPromise(requestPromise));
        if ((System.currentTimeMillis() - this.requestPromise.getPromiseSession().getCreationTime() + Overseer.getRefreshTime()) >= Overseer.getTIMEOUT()) {
            requestPromise.changeState(new TimedOutPromise(requestPromise));
            this.requestPromise.checkForTimeout();
        }


    }
}
