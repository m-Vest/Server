package mvest.core.asset.handler;

import mvest.common.event.Event;
import mvest.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(Event<T> event);
    boolean supports(Event<T> event);
}
