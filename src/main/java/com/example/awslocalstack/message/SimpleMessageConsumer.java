package com.example.awslocalstack.message;

import com.example.awslocalstack.model.Event;
import io.awspring.cloud.sqs.annotation.SnsNotificationMessage;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleMessageConsumer {

    private static final String ORDER_TRANSMISSION_QUEUE = "test-order-transmission-queue";

    private static final String ORDER_DISPATCH_QUEUE = "test-order-dispatch-queue";

    // 기본 acknowledgement 모드(ON_SUCCESS): 리스너가 정상 종료되면 메시지 삭제
    @SqsListener(ORDER_TRANSMISSION_QUEUE)
    public void consumeOrderTransmission(@SnsNotificationMessage Event event) {
        if (event != null) {
            log.info("[{}] : {}", ORDER_TRANSMISSION_QUEUE, event);
        }
    }

    @SqsListener(ORDER_DISPATCH_QUEUE)
    public void consumeOrderDispatch(@SnsNotificationMessage Event event) {
        if (event != null) {
            log.info("[{}] : {}", ORDER_DISPATCH_QUEUE, event);
        }
    }

}
