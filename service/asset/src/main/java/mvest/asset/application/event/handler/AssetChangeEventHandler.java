package mvest.asset.application.event.handler;

import lombok.RequiredArgsConstructor;
import mvest.asset.application.AssetCommandService;
import mvest.common.event.Event;
import mvest.common.event.EventType;
import mvest.common.event.payload.AssetChangeEventPayload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetChangeEventHandler implements EventHandler<AssetChangeEventPayload> {

    private final AssetCommandService assetCommandService;

    @Override
    public void handle(Event<AssetChangeEventPayload> event) {
        AssetChangeEventPayload payload = event.getPayload();
        assetCommandService.applyAssetChange(payload);
    }

    @Override
    public boolean supports(Event<AssetChangeEventPayload> event) {
        return event.getType() == EventType.APPLY_ASSET_CHANGE;
    }
}
