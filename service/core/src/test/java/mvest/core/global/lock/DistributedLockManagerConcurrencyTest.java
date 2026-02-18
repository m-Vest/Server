package mvest.core.global.lock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DistributedLockManagerConcurrencyTest {

    @Autowired
    private DistributedLockManager lockManager;

    private static final String LOCK_KEY = "test:lock";
    private static final long TTL = 10_000;

    @Test
    @DisplayName("동시에 여러 요청이 와도 하나만 락 획득 성공")
    void onlyOneInstanceShouldAcquireLock() throws InterruptedException {

        int threadCount = 10;

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        List<Boolean> results = new CopyOnWriteArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    boolean locked = lockManager.tryLock(LOCK_KEY, TTL);
                    results.add(locked);

                    if (locked) {
                        // 잠깐 점유
                        Thread.sleep(100);
                        lockManager.releaseLock(LOCK_KEY);
                    }

                } catch (Exception ignored) {
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        long successCount = results.stream()
                .filter(Boolean::booleanValue)
                .count();

        assertThat(successCount).isEqualTo(1);
    }
}
