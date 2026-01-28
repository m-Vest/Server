package mvest.core.user.infrastructure;

import lombok.RequiredArgsConstructor;
import mvest.core.user.application.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;
}
