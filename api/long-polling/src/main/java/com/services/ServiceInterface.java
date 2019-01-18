package com.services;

import com.entity.Resolvable;

import java.util.Optional;

public interface ServiceInterface {
    Optional<Resolvable> resolve();
    default void notifyOfChange(Resolvable resolvable){
    }
}
