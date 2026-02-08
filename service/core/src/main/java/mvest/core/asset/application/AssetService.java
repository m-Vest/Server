package mvest.core.asset.application;

import lombok.RequiredArgsConstructor;
import mvest.common.event.Event;
import mvest.common.event.EventPayload;
import org.springframework.stereotype.Service;
import mvest.core.asset.application.handler.EventHandler;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final List<EventHandler> eventHandlers;

    public void handleEvent(Event<EventPayload> event) {
        for (EventHandler eventHandler : eventHandlers) {
            if (eventHandler.supports(event)) {
                eventHandler.handle(event);
            }
        }
    }
}
