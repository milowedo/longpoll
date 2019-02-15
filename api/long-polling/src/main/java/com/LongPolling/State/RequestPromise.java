package com.LongPolling.State;

import com.LongPolling.HangingRequest;
import com.LongPolling.Resolvable;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpSession;

public class RequestPromise extends DeferredResult<Resolvable> implements HangingRequest{

    private HttpSession promiseSession;
    private final String expectedReturnType;
    private STATEPromise state;

    public RequestPromise(String classType) {
        this.expectedReturnType = classType;
        this.state = new STATEHangingPromise(this);
    }

    public STATEPromise getState() {
        return state;
    }

    void changeState(STATEPromise state){
        this.state = state;
    }
    HttpSession getPromiseSession() {
        return promiseSession;
    }
    String getExpectedReturnType() {
        return expectedReturnType;
    }
    public void checkForTimeout(){
        state.checkForTimeout();
    }
    public void setSession(HttpSession session) { this.promiseSession = session; }

    @Override
    public boolean checkIfResolved() {
        if(!(this.state instanceof STATEResolvedPromise)){
            this.checkForTimeout();
            return this.state instanceof STATEResolvedPromise;
        }else{
            return this.state instanceof STATEResolvedPromise;
        }
    }
    public void update(Resolvable resolved) {
        state.update(resolved);
    }

}
