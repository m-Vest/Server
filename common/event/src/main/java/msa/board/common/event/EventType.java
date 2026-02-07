package msa.board.common.event;

import msa.board.common.event.payload.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.board.common.event.payload.*;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    // Core → Order
    ORDER_SUBMITTED(OrderSubmittedEventPayload.class, Topic.MVEST_ORDER),

    // Order → Core
    ORDER_EXECUTED(OrderExecutedEventPayload.class, Topic.MVEST_CORE),
    ORDER_REJECTED(OrderRejectedEventPayload.class, Topic.MVEST_CORE),

    // Order → Asset
    APPLY_ASSET_CHANGE(AssetChangeEventPayload.class, Topic.MVEST_ASSET),

    // Asset → Core
    ASSET_APPLIED(AssetAppliedEventPayload.class, Topic.MVEST_CORE);

    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type) {
        try {
            return valueOf(type);
        } catch (Exception e) {
            log.error("[EventType.from] type={}", type, e);
            return null;
        }
    }

    public static class Topic {
        public static final String MVEST_CORE = "mvest-core";
        public static final String MVEST_ORDER = "mvest-order";
        public static final String MVEST_ASSET = "mvest-asset";
    }
}
