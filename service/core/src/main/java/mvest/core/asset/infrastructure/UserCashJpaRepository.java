package mvest.core.asset.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCashJpaRepository extends JpaRepository<UserCashEntity, Long> {
    @Query("select u.userId from UserCashEntity u")
    List<Long> findAllUserIds();
}
