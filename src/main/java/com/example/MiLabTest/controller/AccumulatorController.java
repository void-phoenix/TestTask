package com.example.MiLabTest.controller;

import com.example.MiLabTest.processor.ServiceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;


@RestController
public class AccumulatorController {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/accumulate")
    public Mono<Long> accumulate(ServerWebExchange exchange) {
        return exchange.getFormData().flatMap( data -> {
            List<String> keys = new ArrayList<>(data.keySet());
            Long digit = 0L;
            try {
                digit = Long.valueOf(keys.get(0).replaceAll("'", ""));
            } catch (NumberFormatException ex) {
                System.out.println(ex.getMessage());
            }
            CompletableFuture<Long> sum = new CompletableFuture<>();
            applicationEventPublisher.publishEvent(new ServiceEvent(this, sum, digit, ServiceEvent.TYPE.ACCUMULATE));
            return Mono.fromFuture(sum);
        });
    }


    @GetMapping("/calculate")
    public Mono<Long> calculate() throws Exception{
        CompletableFuture<Long> sum = new CompletableFuture<>();
        applicationEventPublisher.publishEvent(new ServiceEvent(this, sum, 0L, ServiceEvent.TYPE.CALCULATE));
        return Mono.fromFuture(sum);
    }
}
