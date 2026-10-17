package com.example.awslocalstack.service;

import com.example.awslocalstack.message.SimpleMessageProducer;
import com.example.awslocalstack.model.Event;
import org.springframework.stereotype.Service;

@Service
public class SnsService {

    private final SimpleMessageProducer simpleMessageProducer;

    public SnsService(SimpleMessageProducer simpleMessageProducer) {
        this.simpleMessageProducer = simpleMessageProducer;
    }

    public void publish(Event event) {
        simpleMessageProducer.publish(event);
    }

}
