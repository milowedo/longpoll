package com.LongPolling.State;

import com.LongPolling.Overseer;

public class ReadyPromise extends PromiseState {

    public ReadyPromise(RequestPromise requestPromise) {
        super(requestPromise);
    }

    @Override
    public void checkForTimeout() {
        if ((System.currentTimeMillis() - this.requestPromise.getPromiseSession().getCreationTime() + Overseer.getRefreshTime()) > Overseer.getTIMEOUT()) {
            requestPromise.changeState(new TimedOutPromise(requestPromise));
            this.checkForTimeout();
        }
    }
}
