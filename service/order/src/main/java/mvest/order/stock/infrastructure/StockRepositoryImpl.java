package mvest.order.stock.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mvest.order.global.code.InfrastructureErrorCode;
import mvest.order.global.exception.InfrastructureException;
import mvest.order.stock.application.StockRepository;
import mvest.order.stock.domain.StockPrice;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {

    private final StockPriceRedisRepository stockPriceRedisRepository;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<StockPrice> findByStockCode(String stockCode) {
        String data = stockPriceRedisRepository.findAllRaw();

        if (data == null) {
            return Optional.empty();
        }

        try {
            JsonNode root = objectMapper.readTree(data);
            JsonNode pricesNode = root.get("prices");

            if (pricesNode == null || !pricesNode.isArray()) {
                return Optional.empty();
            }

            for (JsonNode item : pricesNode) {
                if (stockCode.equals(item.get("stockCode").asText())) {
                    StockPrice stockPrice =
                            objectMapper.convertValue(item, StockPrice.class);
                    return Optional.of(stockPrice);
                }
            }

            return Optional.empty();

        } catch (Exception e) {
            throw new InfrastructureException(InfrastructureErrorCode.REDIS_PARSE_ERROR);
        }
    }
}
