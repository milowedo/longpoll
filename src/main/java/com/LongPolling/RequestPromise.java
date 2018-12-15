package com.LongPolling;

import com.entity.Customer;

import com.service.ServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class RequestPromise extends DeferredResult<Customer> implements HangingRequest {

    private final ServiceInterface customerService;
    private HttpSession promiseSession;
    private final Logger logger = LoggerFactory.getLogger(RequestPromise.class);


    public RequestPromise(ServiceInterface customerService){
        this.customerService = customerService;
    }

    public boolean execute() {
        logger.info("");
        Optional<Customer> resolved = customerService.resolve();
        if( (System.currentTimeMillis() - this.promiseSession.getCreationTime() + Overseer.refreshTime) > 30000 ) {
            this.setErrorResult("timedOut");
            return true;
        }
        if(resolved.isPresent()){
            logger.info("");
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
