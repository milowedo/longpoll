package com.LongPolling;

import com.entity.Customer;

import com.service.ServiceInterface;
import org.springframework.web.context.request.async.DeferredResult;
import java.util.Optional;

public class RequestPromise extends DeferredResult<Customer> implements HangingRequest {

    private ServiceInterface customerService;

    public RequestPromise(ServiceInterface customerService){
        this.customerService = customerService;
    }

    public boolean execute() {
        System.out.println(Thread.currentThread() + " IN CLASS:: " + this.getClass().getSimpleName() + ":: EXECUTING execute()");
        Optional<Customer> resolved = customerService.resolve();
        if(resolved.isPresent()){
            System.out.println(this.getClass().getSimpleName() + ":: resolved correctly");
            this.setResult(resolved.get());
            return true;
        }else {
            return false;
        }

    }
}
