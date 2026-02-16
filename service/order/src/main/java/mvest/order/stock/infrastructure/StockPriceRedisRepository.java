package mvest.order.stock.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StockPriceRedisRepository {

    private static final String KEY = "mvest:stock:price:KR";

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String findAllRaw() {
        return redisTemplate.opsForValue().get(KEY);
    }
}
