package com.example.awslocalstack.message;

import com.example.awslocalstack.model.Event;
import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Slf4j
@Service
public class SimpleMessageProducer {

    private static final String ORDER_TOPIC = "test-order-topic";

    private static final ZoneId ZONE_SEOUL = ZoneId.of("Asia/Seoul");

    private final SnsTemplate snsTemplate;

    private final String version;

    public SimpleMessageProducer(SnsTemplate snsTemplate, @Value("${message.version}") String version) {
        this.snsTemplate = snsTemplate;
        this.version = version;
    }

    public void publish(Event event) {
        event.setEventId(UUID.randomUUID().toString());
        event.setVersion(version);
        event.setOccurredAt(LocalDateTime.now(ZONE_SEOUL).toString());
        snsTemplate.convertAndSend(ORDER_TOPIC, event);
    }

}
