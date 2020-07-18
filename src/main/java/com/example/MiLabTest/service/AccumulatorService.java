package com.example.MiLabTest.service;

import com.example.MiLabTest.processor.ServiceEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
public class AccumulatorService {

    private List<CompletableFuture<Long>> subscribers = new ArrayList<>();
    private long sum;


    @EventListener
    private void process(ServiceEvent event) {
        subscribers.add(event.getCompletableFuture());
        if (event.getType() == ServiceEvent.TYPE.ACCUMULATE) {
            sum+= event.getValue();
        } else {
            subscribers.forEach(s -> s.complete(sum));
            sum = 0;
            subscribers.clear();
            System.out.println("done");
        }
    }
}
