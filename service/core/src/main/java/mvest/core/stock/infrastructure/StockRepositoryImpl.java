package mvest.core.stock.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mvest.core.global.code.InfrastructureErrorCode;
import mvest.core.global.exception.InfrastructureException;
import mvest.core.stock.application.StockRepository;
import mvest.core.stock.domain.StockDataStatus;
import mvest.core.stock.domain.StockPrice;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {

    private final StockPriceRedisRepository stockPriceRedisRepository;
    private final ObjectMapper objectMapper;

    @Override
    public List<StockPrice> findAll() {

        String data = stockPriceRedisRepository.findAllRaw();

        if (data == null) {
            return List.of();
        }

        try {
            JsonNode root = objectMapper.readTree(data);
            JsonNode pricesNode = root.get("prices");

            if (pricesNode == null || !pricesNode.isArray()) {
                return List.of();
            }

            return objectMapper.convertValue(
                    pricesNode,
                    new TypeReference<List<StockPrice>>() {}
            );

        } catch (Exception e) {
            throw new InfrastructureException(InfrastructureErrorCode.REDIS_PARSE_ERROR);
        }
    }

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

    @Override
    public StockDataStatus getDataStatus() {

        String data = stockPriceRedisRepository.findAllRaw();

        if (data == null) {
            return StockDataStatus.DELAYED;
        }

        try {
            JsonNode root = objectMapper.readTree(data);
            JsonNode updatedAtNode = root.get("updatedAt");

            if (updatedAtNode == null) {
                return StockDataStatus.DELAYED;
            }

            LocalDateTime updatedAt =
                    LocalDateTime.parse(updatedAtNode.asText());

            Duration duration =
                    Duration.between(updatedAt, LocalDateTime.now());

            if (duration.toMinutes() >= 10) {
                return StockDataStatus.DELAYED;
            }

            return StockDataStatus.NORMAL;

        } catch (Exception e) {
            throw new InfrastructureException(InfrastructureErrorCode.REDIS_PARSE_ERROR);
        }
    }
}
