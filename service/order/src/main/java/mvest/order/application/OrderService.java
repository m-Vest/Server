package mvest.order.application;

import lombok.RequiredArgsConstructor;
import mvest.common.event.Event;
import mvest.common.event.EventPayload;
import mvest.order.application.handler.EventHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final List<EventHandler> eventHandlers;

    public void handleEvent(Event<EventPayload> event) {
        for (EventHandler eventHandler : eventHandlers) {
            if (eventHandler.supports(event)) {
                eventHandler.handle(event);
            }
        }
    }
}
