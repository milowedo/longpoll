package com.LongPolling;

import com.LongPolling.State.RequestPromise;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class Overseer {

    private static final long refreshTime = 600;
    private static final long TIMEOUT = 30000;
    private final Queue<HangingRequest> requests = new ConcurrentLinkedDeque<>();
    private final List<ServiceInterface> services = new LinkedList<>();

    public RequestPromise subscribe(String className, HttpSession session, ServiceInterface service) {
        RequestPromise output = new RequestPromise(className);
        output.setSession(session);
        this.requests.add(output);
        if(!services.contains(service)) services.add(service);
        return output;
    }

    public static long getRefreshTime() {
        return refreshTime;
    }

    public static long getTIMEOUT() {
        return TIMEOUT;
    }

    private void notifyRequests(Resolvable resolved){ requests.forEach((hangingRequest -> hangingRequest.update(resolved))); }

    //CANDO: add an unsubscribe method

    @Scheduled(fixedRate = refreshTime)
    public void scheduled(){

        //CHECK FOR REDUNDANT REQUESTS
        requests.forEach(hangingRequest -> {
            if(hangingRequest.checkIfResolved()) {
                requests.remove(hangingRequest);
            }
        });

        //CHECK FOR NEW DATA
        services.forEach(serviceInterface -> {
            Optional<Resolvable> resolved = serviceInterface.resolve();
            resolved.ifPresent(this::notifyRequests);
        });
    }


}
