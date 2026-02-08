package mvest.asset.infrastructure;

import lombok.RequiredArgsConstructor;
import mvest.asset.application.UserStockRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserStockRepositoryImpl implements UserStockRepository {

    private final UserStockJpaRepository userStockJpaRepository;

    @Override
    public void increase(Long userId, String stockCode, Integer quantity) {
        //TODO
    }

    @Override
    public void decrease(Long userId, String stockCode, Integer quantity) {
        //TODO
    }
}
