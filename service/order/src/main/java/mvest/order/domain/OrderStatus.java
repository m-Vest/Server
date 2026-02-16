package mvest.order.domain;

public enum OrderStatus {
    PENDING,   // 대기
    EXECUTED,  // 체결
    REJECTED,  // 거절 (체결 제한 시간 마감)
    COMPLETED  // 체결 & 자산 반영 완료
}
