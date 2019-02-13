package com.LongPolling.State;

import com.LongPolling.Overseer;
import com.LongPolling.Resolvable;

public class STATEHangingPromise extends STATEPromise {

    STATEHangingPromise(RequestPromise requestPromise) {
        super(requestPromise);
    }

    @Override
    void update(Resolvable resolved) {
        if(resolved.getClass().getName().equals(requestPromise.getExpectedReturnType())) {
            requestPromise.changeState(new STATEReadyPromise(requestPromise));
            requestPromise.update(resolved);
        }
    }

    @Override
    public void checkForTimeout() {
        if(this.requestPromise.getPromiseSession() == null) {
            requestPromise.changeState(new STATEResolvedPromise(requestPromise));
        }

        long currentTime = System.currentTimeMillis();
        long creationTime = this.requestPromise.getPromiseSession().getCreationTime();

        if (( currentTime- creationTime + Overseer.getRefreshTime()) >= Overseer.getTIMEOUT()) {
            requestPromise.changeState(new STATETimedOutPromise(requestPromise));
            this.requestPromise.checkForTimeout();
        }


    }
}
