package mvest.common.outboxmessagerelay;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageRelay {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 트랜잭션 커밋 직전 Outbox 저장 (이벤트 유실 방지)
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void createOutbox(OutboxEvent event) {
        outboxRepository.save(event.getOutbox());
    }

    /**
     * 트랜잭션 커밋 직후 즉시 이벤트 발행 시도
     */
    @Async("messageRelayExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishAfterCommit(OutboxEvent event) {
        publish(event.getOutbox());
    }

    /**
     * 주기적으로(10초) 실패한 이벤트 발행 시도
     */
    @Scheduled(
            fixedDelay = 10,
            initialDelay = 5,
            timeUnit = TimeUnit.SECONDS
    )
    public void publishPendingEvents() {
        List<Outbox> outboxes =
                outboxRepository.findAllByCreatedAtLessThanEqualOrderByCreatedAtAsc(
                        LocalDateTime.now().minusSeconds(10),
                        Pageable.ofSize(100)
                );

        for (Outbox outbox : outboxes) {
            publish(outbox);
        }
    }

    private void publish(Outbox outbox) {
        try {
            kafkaTemplate.send(
                    outbox.getEventType().getTopic(),
                    outbox.getPayload()
            ).get(1, TimeUnit.SECONDS);

            outboxRepository.delete(outbox);
        } catch (Exception e) {
            log.error("[MessageRelay.publish] outbox={}", outbox, e);
        }
    }
}
