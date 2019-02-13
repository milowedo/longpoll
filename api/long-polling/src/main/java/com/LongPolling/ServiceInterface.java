package com.LongPolling;

import java.util.Optional;

public interface ServiceInterface {
    Optional<Resolvable> resolve();
    void notifyOfChange(Resolvable resolvable);
}
