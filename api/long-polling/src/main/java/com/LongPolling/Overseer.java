package com.LongPolling;

import com.LongPolling.State.RequestPromise;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
@Scope("singleton")
public final class Overseer {
    //private static Overseer instance = null;

    private static final long refreshTime = 600;
    private static final long TIMEOUT = 30000;
    private final Queue<HangingRequest> requests = new ConcurrentLinkedDeque<>();
    private final List<ServiceInterface> services = new LinkedList<>();

//Singleton manual implementation, we do not need it, Spring does it for us
//    private Overseer(){
//        System.out.println("Creating the overseer");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//    }
//    public static Overseer getInstance(){
//        if (instance == null){
//            instance = Overseer.instance;
//        }
//        return instance;
//    }

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
