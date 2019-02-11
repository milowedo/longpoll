package com.LongPolling;

import com.entity.Resolvable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public abstract class ServicePoll implements ServiceInterface {

    private Resolvable newDataForResponse = null;

    @Transactional
    public Optional<Resolvable> resolve() {
        if (newDataForResponse == null) {
            return Optional.empty();
        } else {
            ServicePoll thisObj = this;
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            thisObj.newDataForResponse = null;
                        }
                    },
                    500
            );
            return  Optional.of(newDataForResponse);
        }
    }

    @Override
    public void notifyOfChange(Resolvable resolvable) {
        newDataForResponse = resolvable;
    }
}
