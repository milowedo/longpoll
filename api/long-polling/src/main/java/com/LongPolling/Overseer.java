package com.LongPolling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@Component
public class Overseer {

    static final long refreshTime = 1000;
    private final Logger logger = LoggerFactory.getLogger(Overseer.class);

    private final Queue<HangingRequest> responses = new ConcurrentLinkedDeque<>();

    private void executeRequests(){
        responses.forEach((hangingRequest -> {
            logger.info("");
            if (hangingRequest.execute()) {
                responses.remove(hangingRequest);
                hangingRequest.killSession();
            }
        }));
    }

    public void subscribe(HangingRequest hangingRequest){
        this.responses.add(hangingRequest);
    }

    @Scheduled(fixedRate = refreshTime)
    public void seeWhetherAnythingCanBeExecuted(){
        this.executeRequests();
    }

}
