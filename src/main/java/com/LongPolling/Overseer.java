package com.LongPolling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class Overseer {

    private final Queue<HangingRequest> responses = new ConcurrentLinkedDeque<>();

    private void executeRequests(){
        responses.forEach((hangingRequest -> {
            System.out.println(Thread.currentThread() + " IN CLASS:: " + this.getClass().getSimpleName() + " EXECUTING: executeRequests()");
            if (hangingRequest.execute()) {
                responses.remove(hangingRequest);
            }
        }));
    }

    public void subscribe(HangingRequest hangingRequest){
        this.responses.add(hangingRequest);
    }

    @Scheduled(fixedRate = 6000)
    public void seeWhetherAnythingCanBeExecuted(){
        this.executeRequests();
    }

}
