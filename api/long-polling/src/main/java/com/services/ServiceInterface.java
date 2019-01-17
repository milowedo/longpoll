package com.services;

import com.entity.Resolvable;

import java.util.Optional;

public interface ServiceInterface {
    public Optional<Resolvable> resolve();
    void notifyOfChange();
}
