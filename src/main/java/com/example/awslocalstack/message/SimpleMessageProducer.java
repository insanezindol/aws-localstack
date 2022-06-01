package com.example.awslocalstack.message;

import com.example.awslocalstack.model.Event;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.UUID;

@Slf4j
@Service
public class SimpleMessageProducer {

    private static final String ORDER_TOPIC = "test-order-topic";

    @Autowired
    private NotificationMessagingTemplate notificationMessagingTemplate;

    @Value("${message.version}")
    private String VERSION;

    public void publish(Event event) {
        event.setEventId(UUID.randomUUID().toString());
        event.setVersion(VERSION);
        event.setOccurredAt(LocalDateTime.now(TimeZone.getTimeZone("Asia/Seoul").toZoneId()).toString());
        notificationMessagingTemplate.convertAndSend(ORDER_TOPIC, event);
    }

}
