package mvest.core.global.lock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DistributedLockManagerReacquireTest {

    @Autowired
    private DistributedLockManager lockManager;

    private static final String LOCK_KEY = "test:reacquire:lock";
    private static final long TTL = 10_000;

    @Test
    @DisplayName("락 해제 후 다시 획득 가능해야 한다")
    void lockShouldBeReacquirableAfterRelease() throws InterruptedException {

        // 최초 획득
        boolean firstLocked = lockManager.tryLock(LOCK_KEY, TTL);
        assertThat(firstLocked).isTrue();

        // 잠깐 점유
        Thread.sleep(100);

        // 해제
        lockManager.releaseLock(LOCK_KEY);

        // 다시 획득 시도
        boolean secondLocked = lockManager.tryLock(LOCK_KEY, TTL);
        assertThat(secondLocked).isTrue();

        // cleanup
        lockManager.releaseLock(LOCK_KEY);
    }
}
