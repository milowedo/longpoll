package com.LongPolling;

import com.entity.Resolvable;

import java.util.Optional;

public interface ServiceInterface {
    Optional<Resolvable> resolve();
    void notifyOfChange(Resolvable resolvable);
}
