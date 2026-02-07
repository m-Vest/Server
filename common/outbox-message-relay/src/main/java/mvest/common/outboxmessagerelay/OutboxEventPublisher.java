package mvest.common.outboxmessagerelay;

import lombok.RequiredArgsConstructor;
import mvest.common.event.Event;
import mvest.common.event.EventPayload;
import mvest.common.event.EventType;
import mvest.common.snowflake.Snowflake;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {

    private final Snowflake outboxIdSnowflake = new Snowflake();
    private final Snowflake eventIdSnowflake = new Snowflake();

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(EventType type, EventPayload payload) {
        Outbox outbox = Outbox.create(
                outboxIdSnowflake.nextId(),
                type,
                Event.of(
                        eventIdSnowflake.nextId(),
                        type,
                        payload
                ).toJson()
        );

        applicationEventPublisher.publishEvent(OutboxEvent.of(outbox));
    }
}
