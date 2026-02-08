package mvest.core.asset.application.handler;

import lombok.RequiredArgsConstructor;
import mvest.common.event.Event;
import mvest.common.event.EventType;
import mvest.common.event.payload.AssetAppliedEventPayload;
import mvest.core.asset.application.AssetCommandService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetAppliedEventHandler implements EventHandler<AssetAppliedEventPayload> {

    private final AssetCommandService assetCommandService;

    @Override
    public void handle(Event<AssetAppliedEventPayload> event) {
        AssetAppliedEventPayload payload = event.getPayload();
        assetCommandService.updateAssetChange(payload);
    }

    @Override
    public boolean supports(Event<AssetAppliedEventPayload> event) {
        return event.getType() == EventType.ASSET_APPLIED;
    }
}
