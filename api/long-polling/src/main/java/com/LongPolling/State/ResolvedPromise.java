package com.LongPolling.State;

public class ResolvedPromise extends PromiseState {

    ResolvedPromise(RequestPromise requestPromise) {
        super(requestPromise);
    }

    @Override
    public void checkForTimeout() {
        this.killSession();
    }

    private void killSession(){
        if(this.requestPromise.getPromiseSession() != null)
            this.requestPromise.getPromiseSession().invalidate();
    }

}
