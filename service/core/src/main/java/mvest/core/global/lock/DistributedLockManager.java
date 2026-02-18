package mvest.core.global.lock;

public interface DistributedLockManager {

    boolean tryLock(String key, long ttlMillis);

    void releaseLock(String key);
}
