package com.example.awslocalstack.message;

import com.example.awslocalstack.model.Event;
import io.awspring.cloud.messaging.config.annotation.NotificationMessage;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;


@Slf4j
@Controller
public class SimpleMessageConsumer {

    private static final String ORDER_TRANSMISSION_QUEUE = "test-order-transmission-queue";

    private static final String ORDER_DISPATCH_QUEUE = "test-order-dispatch-queue";

    @SqsListener(value = ORDER_TRANSMISSION_QUEUE, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void consumeOrderTransmission(@NotificationMessage Event event) {
        if (event != null) {
            log.info("[{}] : {}", ORDER_TRANSMISSION_QUEUE, event);
        }
    }

    @SqsListener(value = ORDER_DISPATCH_QUEUE, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void consumeOrderDispatch(@NotificationMessage Event event) {
        if (event != null) {
            log.info("[{}] : {}", ORDER_DISPATCH_QUEUE, event);
        }
    }

}
