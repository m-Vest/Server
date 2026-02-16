package mvest.order.application.handler;

import lombok.RequiredArgsConstructor;
import mvest.common.event.Event;
import mvest.common.event.EventType;
import mvest.common.event.payload.OrderSubmittedEventPayload;
import mvest.order.application.OrderCommandService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderSubmittedEventHandler implements EventHandler<OrderSubmittedEventPayload> {

    private final OrderCommandService orderCommandService;

    @Override
    public void handle(Event<OrderSubmittedEventPayload> event) {
        OrderSubmittedEventPayload payload = event.getPayload();
        orderCommandService.executeOrder(payload);
    }

    @Override
    public boolean supports(Event<OrderSubmittedEventPayload> event) {
        return event.getType() == EventType.ORDER_SUBMITTED;
    }
}
