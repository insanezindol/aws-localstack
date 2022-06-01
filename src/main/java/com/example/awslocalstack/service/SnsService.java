package com.example.awslocalstack.service;

import com.example.awslocalstack.message.SimpleMessageProducer;
import com.example.awslocalstack.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.UUID;

@Slf4j
@Service
public class SnsService {

    final SimpleMessageProducer simpleMessageProducer;

    public SnsService(SimpleMessageProducer simpleMessageProducer) {
        this.simpleMessageProducer = simpleMessageProducer;
    }

    public void publish(Event event) {
        simpleMessageProducer.publish(event);
    }

}
