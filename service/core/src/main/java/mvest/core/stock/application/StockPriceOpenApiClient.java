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
            "373220", // LG에너지솔루션
            "207940", // 삼성바이오로직스
            "005935", // 삼성전자우
            "006400", // 삼성SDI
            "051910", // LG화학
            "035420", // NAVER
            "035720", // 카카오
            "105560", // KB금융
            "055550", // 신한지주
            "012330", // 현대모비스
            "005380", // 현대차
            "068270", // 셀트리온
            "028260", // 삼성물산
            "017670", // SK텔레콤
            "003670", // 포스코홀딩스
            "086790", // 하나금융지주
            "015760", // 한국전력
            "032830"  // 삼성생명
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
