package mvest.order;

import mvest.common.event.payload.OrderSubmittedEventPayload;
import mvest.common.event.payload.OrderType;
import mvest.order.application.OrderCommandService;
import mvest.order.stock.application.StockRepository;
import mvest.order.stock.domain.StockPrice;
import mvest.order.infrastructure.OrderJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderCommandServiceConcurrencyTest {

    @Autowired
    private OrderCommandService orderCommandService;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @org.springframework.boot.test.mock.mockito.MockBean
    private StockRepository stockRepository;

    @BeforeEach
    void setUp() {
        // 테스트용 주식 가격 세팅 (즉시 체결 유도)
        StockPrice mockPrice = new StockPrice("TEST", new BigDecimal("150.00"));
        when(stockRepository.findByStockCode("TEST")).thenReturn(Optional.of(mockPrice));
    }

    @Test
    @DisplayName("동일한 주문 ID로 동시에 요청이 오면, 멱등성 방어가 뚫려 DB에 중복으로 데이터가 저장된다")
    void executeOrder_concurrency_test() throws InterruptedException {
        // given
        String uniqueOrderId = String.valueOf(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        int threadCount = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        OrderSubmittedEventPayload payload = OrderSubmittedEventPayload.builder()
                .orderId(uniqueOrderId)
                .userId(1L)
                .stockCode("TEST")
                .orderType(OrderType.BUY)
                .price(new BigDecimal("150.00"))
                .quantity(10)
                .occurredAt(LocalDateTime.now())
                .build();

        AtomicInteger successCount = new AtomicInteger(0);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    orderCommandService.executeOrder(payload);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        // then
        long countInDb = orderJpaRepository.countByOrderId(uniqueOrderId);

        System.out.println("=== 동시성 테스트 결과 ===");
        System.out.println("사용한 주문 ID: " + uniqueOrderId);
        System.out.println("메서드 호출 성공 횟수: " + successCount.get());
        System.out.println("DB에 저장된 해당 주문 개수: " + countInDb);

        assertThat(countInDb).isEqualTo(1);
    }
}
