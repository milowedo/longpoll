package com.LongPolling.State;

import com.LongPolling.HangingRequest;
import com.entity.Resolvable;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpSession;

public class RequestPromise extends DeferredResult<Resolvable> implements HangingRequest{

    private HttpSession promiseSession;
    private String expected;
    private PromiseState state;

    public RequestPromise(String classType) {
        this.expected = classType;
        this.state = new HangingPromise(this);
    }

    public PromiseState getState() {
        return state;
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
    public boolean checkIfResolved() {
        if(!(this.state instanceof ResolvedPromise)){
            System.out.println("not an instance of resolved, but of "+  this.state.getClass() +" :running checkForTimeout");
            this.checkForTimeout();
            return this.state instanceof ResolvedPromise;
        }else{
            return this.state instanceof ResolvedPromise;
        }
    }
    public void update(Resolvable resolved) {
        state.update(resolved);
    }

}
