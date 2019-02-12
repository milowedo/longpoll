package com.LongPolling.State;

import com.LongPolling.HangingRequest;
import com.entity.Resolvable;

import javax.servlet.http.HttpSession;

public class RequestPromise implements HangingRequest {

    private HttpSession promiseSession;
    private String expected;
    private PromiseState state;

    public RequestPromise(String classType) {
        this.expected = classType;
        this.state = new HangingPromise(this);
    }

    void changeState(PromiseState state){
        this.state = state;
    }
    HttpSession getPromiseSession() {
        return promiseSession;
    }
    String getExpected() {
        return expected;
    }
    public void checkForTimeout(){
        state.checkForTimeout();
    }
    public void setSession(HttpSession session) { this.promiseSession = session; }
    @Override
    public boolean checkState() {
        this.checkForTimeout();
        return this.state instanceof ResolvedPromise;
    }

    public boolean update(Resolvable resolved) {
        state.update(resolved);
        if(resolved.getClass().getName().equals(expected)) {
            this.setResult(resolved);
            return true;
        }return false;
    }

}
