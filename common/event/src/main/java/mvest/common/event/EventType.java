package mvest.common.event;

import mvest.common.event.payload.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mvest.common.event.payload.*;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    // Core → Order
    ORDER_SUBMITTED(OrderSubmittedEventPayload.class, Topic.MVEST_ORDER),

    // Core → Order, Asset
    USER_REGISTERED(UserRegisteredEventPayload.class, Topic.USER_REGISTERED),
    USER_WITHDRAWN(UserWithdrawnEventPayload.class, Topic.USER_WITHDRAWN),

    // Order → Core
    ORDER_EXECUTED(OrderExecutedEventPayload.class, Topic.MVEST_CORE),
    ORDER_REJECTED(OrderRejectedEventPayload.class, Topic.MVEST_CORE),

    // Order → Asset
    APPLY_ASSET_CHANGE(AssetChangeEventPayload.class, Topic.MVEST_ASSET),

    // Asset → Core
    ASSET_APPLIED(AssetChangeEventPayload.class, Topic.MVEST_CORE);

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
        public static final String USER_REGISTERED = "user.registered";
        public static final String USER_WITHDRAWN  = "user.withdrawn";
    }
}
