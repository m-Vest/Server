package mvest.core.stock.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mvest.core.global.code.InfrastructureErrorCode;
import mvest.core.global.exception.InfrastructureException;
import mvest.core.stock.domain.StockPrice;
import mvest.core.stock.infrastructure.NaverFinanceFeignClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StockPriceOpenApiClient {

    private final NaverFinanceFeignClient feignClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final List<String> MAJOR_STOCKS = List.of(
            "005930", // 삼성전자
            "000660", // SK하이닉스
            "035420"  // NAVER
    );

    public List<StockPrice> fetchStockPrices() {
        List<StockPrice> result = new ArrayList<>();

        for (String code : MAJOR_STOCKS) {
            String query = "SERVICE_ITEM:" + code;
            String response = feignClient.getRealtimePrices(query);
            result.addAll(parse(response));
        }

        return result;
    }

    private List<StockPrice> parse(String json) {
        try {
            JsonNode root = objectMapper.readTree(json);
            JsonNode areas = root.at("/result/areas");

            List<StockPrice> result = new ArrayList<>();

            if (!areas.isArray()) {
                return result;
            }

            for (JsonNode area : areas) {
                JsonNode datas = area.get("datas");
                if (datas == null || !datas.isArray()) {
                    continue;
                }

                for (JsonNode item : datas) {
                    result.add(new StockPrice(
                            item.get("cd").asText(),  // 종목 코드
                            item.get("nm").asText(),  // 종목명
                            item.get("nv").asLong(),  // 현재가
                            item.get("cv").asLong(),  // 전일 대비
                            item.get("cr").asDouble() // 등락률
                    ));
                }
            }

            return result;
        } catch (Exception e) {
            throw new InfrastructureException(InfrastructureErrorCode.NAVER_FETCH_ERROR);
        }
    }
}
