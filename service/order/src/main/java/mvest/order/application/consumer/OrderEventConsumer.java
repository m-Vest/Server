package mvest.order.application.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mvest.common.event.Event;
import mvest.common.event.EventPayload;
import mvest.common.event.EventType;
import mvest.order.application.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = {
            EventType.Topic.MVEST_ORDER
    })
    public void listen(String message, Acknowledgment ack) {
        log.info("[OrderEventConsumer.listen] message={}", message);
        Event<EventPayload> event = Event.fromJson(message);
        if (event != null) {
            orderService.handleEvent(event);
        }
        ack.acknowledge();
    }
}
