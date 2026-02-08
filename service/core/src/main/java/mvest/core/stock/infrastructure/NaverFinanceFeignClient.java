package mvest.core.stock.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "naverFinanceClient",
        url = "https://polling.finance.naver.com"
)
public interface NaverFinanceFeignClient {

    @GetMapping("/api/realtime")
    String getRealtimePrices(
            @RequestParam("query") String query
    );
}
