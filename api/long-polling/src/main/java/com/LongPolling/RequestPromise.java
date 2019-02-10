package com.LongPolling;

import com.entity.Resolvable;
import com.services.ServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.async.DeferredResult;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class RequestPromise extends DeferredResult<Resolvable> implements HangingRequest {

    private final ServiceInterface serviceInterface;
    private HttpSession promiseSession;
    private final Logger logger = LoggerFactory.getLogger(RequestPromise.class);
    private final int TIMEOUT = 30000;

    public RequestPromise(ServiceInterface serviceInterface){
        this.serviceInterface = serviceInterface;
    }

    public boolean update() {
        Optional<Resolvable> resolved = serviceInterface.resolve();
        if( (System.currentTimeMillis() - this.promiseSession.getCreationTime() + Overseer.refreshTime) > TIMEOUT ) {
            this.setErrorResult("timedOutSORRY");
            return true;
        }
        if(resolved.isPresent()){
            this.setResult(resolved.get());
            return true;
        }else{
            return false;
        }
    }

    public void killSession(){
        assert this.promiseSession != null;
        this.promiseSession.invalidate();
    }

    public void setSession(HttpSession session) { this.promiseSession = session; }
}
