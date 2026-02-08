package mvest.core.asset.application.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mvest.common.event.Event;
import mvest.common.event.EventPayload;
import mvest.common.event.EventType;
import mvest.core.asset.application.AssetService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AssetEventConsumer {

    private final AssetService assetService;

    @KafkaListener(topics = {
            EventType.Topic.MVEST_CORE
    })
    public void listen(String message, Acknowledgment ack) {
        log.info("[AssetEventConsumer.listen] message={}", message);
        Event<EventPayload> event = Event.fromJson(message);
        if (event != null) {
            assetService.handleEvent(event);
        }
        ack.acknowledge();
    }
}
