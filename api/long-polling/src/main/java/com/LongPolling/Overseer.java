package com.LongPolling;

import com.LongPolling.State.HangingPromise;
import com.LongPolling.State.RequestPromise;
import com.entity.Resolvable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class Overseer {

    public static final long refreshTime = 600;
    private final Logger logger = LoggerFactory.getLogger(Overseer.class);
    private final Queue<HangingRequest> requests = new ConcurrentLinkedDeque<>();
    private final List<ServiceInterface> services = new LinkedList<>();

    private void notifyRequests(Resolvable resolved){
        requests.forEach((hangingRequest -> {
            if (hangingRequest.update(resolved)) {
                requests.remove(hangingRequest);
                hangingRequest.killSession();
            }
        }));
    }

    public RequestPromise subscribe(String className, HttpSession session, ServiceInterface service) {
        RequestPromise output = new HangingPromise(className);
        output.setSession(session);
        this.requests.add(output);
        if(!services.contains(service)) services.add(service);
        return output;
    }

    //CANDO: add an unsubscribe method

    @Scheduled(fixedRate = refreshTime)
    public void scheduled(){

        requests.forEach(hangingRequest -> {
            if(hangingRequest.checkForTimeout()) {
                requests.remove(hangingRequest);
                hangingRequest.killSession();
            } });

        services.forEach(serviceInterface -> {
            Optional<Resolvable> resolved = serviceInterface.resolve();
            resolved.ifPresent(this::notifyRequests);
        });
    }


}
