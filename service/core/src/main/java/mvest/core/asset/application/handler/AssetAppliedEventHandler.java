package mvest.core.asset.application.handler;

import lombok.RequiredArgsConstructor;
import mvest.common.event.Event;
import mvest.common.event.EventType;
import mvest.common.event.payload.AssetChangeEventPayload;
import mvest.core.asset.application.AssetCommandService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetAppliedEventHandler implements EventHandler<AssetChangeEventPayload> {

    private final AssetCommandService assetCommandService;

    @Override
    public void handle(Event<AssetChangeEventPayload> event) {
        AssetChangeEventPayload payload = event.getPayload();
        assetCommandService.applyAssetChange(payload);
    }

    @Override
    public boolean supports(Event<AssetChangeEventPayload> event) {
        return event.getType() == EventType.ASSET_APPLIED;
    }
}
