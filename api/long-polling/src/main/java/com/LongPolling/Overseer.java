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

    private static final long refreshTime = 600;
    private static final long TIMEOUT = 30000;
//    private final Logger logger = LoggerFactory.getLogger(Overseer.class);
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

    private void notifyRequests(Resolvable resolved){
        requests.forEach((hangingRequest -> {
            if (hangingRequest.update(resolved)) {
                requests.remove(hangingRequest);
                hangingRequest.killSession();
            }
        }));
    }

    //CANDO: add an unsubscribe method

    @Scheduled(fixedRate = refreshTime)
    public void scheduled(){

        requests.forEach(hangingRequest -> {
            long millis = System.currentTimeMillis() % 1000;
            if(hangingRequest.checkState()) {
                requests.remove(hangingRequest);
            }
            System.out.println("Timeout took: " + (System.currentTimeMillis() - millis));
        });

        services.forEach(serviceInterface -> {
            Optional<Resolvable> resolved = serviceInterface.resolve();
            resolved.ifPresent(this::notifyRequests);
        });
    }


}
