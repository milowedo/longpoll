package com.LongPolling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class Overseer {

    static final long refreshTime = 600;
    private final Logger logger = LoggerFactory.getLogger(Overseer.class);
    private final Queue<HangingRequest> requests = new ConcurrentLinkedDeque<>();

    private void notifyRequests(){
        requests.forEach((hangingRequest -> {
            if (hangingRequest.update()) {
                requests.remove(hangingRequest);
                hangingRequest.killSession();
            }
        }));
    }

    public void subscribe(HangingRequest hangingRequest){
        this.requests.add(hangingRequest);
    }

    //TODO add an unsubscribe method

    @Scheduled(fixedRate = refreshTime)
    public void seeWhetherAnythingCanBeUpdated(){
        this.notifyRequests();
    }

}
