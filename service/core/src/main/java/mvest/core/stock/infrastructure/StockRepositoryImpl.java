package mvest.core.stock.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mvest.core.global.code.InfrastructureErrorCode;
import mvest.core.global.code.StockErrorCode;
import mvest.core.global.exception.BusinessException;
import mvest.core.global.exception.InfrastructureException;
import mvest.core.stock.application.StockRepository;
import mvest.core.stock.domain.StockPrice;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
