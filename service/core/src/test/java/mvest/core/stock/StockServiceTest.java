package mvest.core.stock.application;

import mvest.core.stock.domain.StockDataStatus;
import mvest.core.stock.domain.StockPrice;
import mvest.core.stock.dto.response.StockPriceDTO;
import mvest.core.stock.dto.response.StockPriceListDTO;
import mvest.core.stock.infrastructure.StockRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private StockRepositoryImpl stockRepository;

    @InjectMocks
    private StockService stockService;

    private StockPrice mockStock() {
        return new StockPrice(
                "005930",
                "삼성전자",
                BigDecimal.valueOf(72000),
                -500,
                -0.69
        );
    }

    @Test
    @DisplayName("주가 데이터가 10분 이내이면 dataStatus는 NORMAL이다")
    void givenFreshData_whenGetStockPrice_thenStatusIsNormal() {
        // given
        StockPrice stock = mockStock();

        given(stockRepository.findByStockCode("005930"))
                .willReturn(Optional.of(stock));
        given(stockRepository.getDataStatus())
                .willReturn(StockDataStatus.NORMAL);

        // when
        StockPriceDTO response = stockService.getStockPrice("005930");

        // then
        assertThat(response.dataStatus()).isEqualTo(StockDataStatus.NORMAL);
    }

    @Test
    @DisplayName("주가 데이터가 10분 초과되면 dataStatus는 DELAYED이다")
    void givenDelayedData_whenGetStockPrice_thenStatusIsDelayed() {
        // given
        StockPrice stock = mockStock();

        given(stockRepository.findByStockCode("005930"))
                .willReturn(Optional.of(stock));
        given(stockRepository.getDataStatus())
                .willReturn(StockDataStatus.DELAYED);

        // when
        StockPriceDTO response = stockService.getStockPrice("005930");

        // then
        assertThat(response.dataStatus()).isEqualTo(StockDataStatus.DELAYED);
    }

    @Test
    @DisplayName("전체 조회 시 dataStatus가 정상적으로 포함된다")
    void givenFreshData_whenGetAllStockPrices_thenStatusIsIncluded() {
        // given
        StockPrice stock = mockStock();

        given(stockRepository.findAll())
                .willReturn(List.of(stock));
        given(stockRepository.getDataStatus())
                .willReturn(StockDataStatus.NORMAL);

        // when
        StockPriceListDTO response = stockService.getAllStockPrices();

        // then
        assertThat(response.dataStatus()).isEqualTo(StockDataStatus.NORMAL);
        assertThat(response.stockPriceDtoList())
                .allMatch(dto -> dto.dataStatus() == StockDataStatus.NORMAL);
    }
}
