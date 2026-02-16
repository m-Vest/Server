package mvest.core.order.infrastructure;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mvest.common.event.payload.OrderType;
import mvest.core.order.domain.OrderStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String orderId;

    private Long userId;
    private String stockCode;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    private BigDecimal price;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public OrderEntity(Long id,
                       String orderId,
                       Long userId,
                       String stockCode,
                       OrderType orderType,
                       BigDecimal price,
                       int quantity,
                       OrderStatus status) {
        this.id = id;
        this.orderId = orderId;
        this.userId = userId;
        this.stockCode = stockCode;
        this.orderType = orderType;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }
}
