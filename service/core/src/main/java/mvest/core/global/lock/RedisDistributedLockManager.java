package mvest.core.global.lock;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisDistributedLockManager implements DistributedLockManager {

    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean tryLock(String key, long ttlMillis) {

        Boolean success = redisTemplate.opsForValue().setIfAbsent(
                key,
                "locked",
                Duration.ofMillis(ttlMillis)
        );

        return Boolean.TRUE.equals(success);
    }

    @Override
    public void releaseLock(String key) {
        redisTemplate.delete(key);
    }
}
