package mvest.core.asset.handler;

import lombok.RequiredArgsConstructor;
import mvest.common.event.Event;
import mvest.common.event.EventType;
import mvest.common.event.payload.AssetChangeEventPayload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetChangedEventHandler implements EventHandler<AssetChangeEventPayload> {

    @Override
    public void handle(Event<AssetChangeEventPayload> event) {
        AssetChangeEventPayload payload = event.getPayload();
    }

    @Override
    public boolean supports(Event<AssetChangeEventPayload> event) {
        return EventType.APPLY_ASSET_CHANGE == event.getType();
    }
}
