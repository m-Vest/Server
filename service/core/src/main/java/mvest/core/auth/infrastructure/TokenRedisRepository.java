package mvest.core.auth.infrastructure;

import org.springframework.data.repository.CrudRepository;

public interface TokenRedisRepository extends CrudRepository<TokenEntity, Long> {
}
