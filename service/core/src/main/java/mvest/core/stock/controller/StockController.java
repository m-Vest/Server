package mvest.core.stock.controller;

import lombok.RequiredArgsConstructor;
import mvest.core.stock.application.StockService;
import mvest.core.stock.dto.response.StockPriceListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
public class StockController {

    private final StockService stockService;

    @GetMapping("/prices")
    public ResponseEntity<StockPriceListDTO> getPrices() {
        return ResponseEntity.ok(stockService.getAllStockPrices());
    }
}
