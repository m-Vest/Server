package mvest.core.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mvest.core.auth.annotation.UserId;
import mvest.core.global.code.CommonSuccessCode;
import mvest.core.global.dto.ResponseDTO;
import mvest.core.order.dto.request.OrderCreateDTO;
import mvest.core.order.dto.response.OrderCreatedDTO;
import mvest.core.order.application.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseDTO<OrderCreatedDTO> createOrder(
            @Valid @RequestBody OrderCreateDTO orderCreateDTO
            // @UserId Long userId
    ) {
        Long userId = 1L;
        return ResponseDTO.success(CommonSuccessCode.CREATED, orderService.createOrder(orderCreateDTO, userId));
    }
}
