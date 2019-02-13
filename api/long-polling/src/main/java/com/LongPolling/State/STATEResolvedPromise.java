package com.LongPolling.State;

import com.LongPolling.Resolvable;

public class STATEResolvedPromise extends STATEPromise {

    STATEResolvedPromise(RequestPromise requestPromise) {
        super(requestPromise);
    }

    @Override
    void update(Resolvable resolved) {
        this.killSession();
    }

    @Override
    public void checkForTimeout() {
        this.killSession();
    }

    private synchronized void killSession(){
        if(this.requestPromise.getPromiseSession() != null) {
            this.requestPromise.getPromiseSession().invalidate();
        }
    }

}
