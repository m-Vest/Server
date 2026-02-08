package mvest.core.stock.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mvest.core.global.code.InfrastructureErrorCode;
import mvest.core.global.exception.InfrastructureException;
import mvest.core.stock.domain.StockPrice;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class StockPriceRedisRepository {

    private static final String KEY = "mvest:stock:price:KR";

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void save(List<StockPrice> prices) {
        try {
            Map<String, Object> payload = Map.of(
                    "updatedAt", LocalDateTime.now().toString(),
                    "prices", prices
            );

            redisTemplate.opsForValue()
                    .set(KEY, objectMapper.writeValueAsString(payload));
        } catch (Exception e) {
            throw new InfrastructureException(InfrastructureErrorCode.REDIS_SAVE_ERROR);
        }
    }

    public String findAllRaw() {
        return redisTemplate.opsForValue().get(KEY);
    }
}
