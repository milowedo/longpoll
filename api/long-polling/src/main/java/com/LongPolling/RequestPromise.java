package com.LongPolling;

import com.entity.Resolvable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.async.DeferredResult;
import javax.servlet.http.HttpSession;

public class RequestPromise extends DeferredResult<Resolvable> implements HangingRequest {

    private HttpSession promiseSession;
    private final Logger logger = LoggerFactory.getLogger(RequestPromise.class);
    private final int TIMEOUT = 30000;
    private String expected;

    public RequestPromise(String classType) {
        this.expected = classType;
    }

    public boolean checkForTimeout(){
        if( (System.currentTimeMillis() - this.promiseSession.getCreationTime() + Overseer.refreshTime) > TIMEOUT ) {
            this.setErrorResult("timedOutSORRY");
            return true;
        }return false;
    }

    public boolean update(Resolvable resolved) {
        if(resolved.getClass().getName().equals(expected)) {
            this.setResult(resolved);
            return true;
        }return false;
    }


    public void killSession(){
        assert this.promiseSession != null;
        this.promiseSession.invalidate();
    }

    public void setSession(HttpSession session) { this.promiseSession = session; }
}
