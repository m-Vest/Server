package mvest.asset.application.event.handler;

import lombok.RequiredArgsConstructor;
import mvest.asset.application.UserCashCommandService;
import mvest.common.event.Event;
import mvest.common.event.EventType;
import mvest.common.event.payload.UserRegisteredEventPayload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegisteredEventHandler implements EventHandler<UserRegisteredEventPayload> {

    private final UserCashCommandService userCashCommandService;

    @Override
    public void handle(Event<UserRegisteredEventPayload> event) {
        UserRegisteredEventPayload payload = event.getPayload();
        userCashCommandService.createInitialCash(payload);
    }

    @Override
    public boolean supports(Event<UserRegisteredEventPayload> event) {
        return EventType.USER_REGISTERED == event.getType();
    }
}
