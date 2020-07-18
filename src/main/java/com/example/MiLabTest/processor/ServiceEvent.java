package com.example.MiLabTest.processor;

import org.springframework.context.ApplicationEvent;

import java.util.concurrent.CompletableFuture;

public class ServiceEvent extends ApplicationEvent {

    private final Long value;
    private final CompletableFuture<Long> completableFuture;
    private final TYPE type;

    public ServiceEvent(Object source, CompletableFuture<Long> future, Long value, TYPE type) {
        super(source);
        this.value = value;
        this.completableFuture = future;
        this.type = type;
    }

    public Long getValue() {
        return value;
    }

    public CompletableFuture<Long> getCompletableFuture() {
        return completableFuture;
    }

    public TYPE getType() {
        return type;
    }

    public enum TYPE {
        ACCUMULATE,
        CALCULATE
    }
}
