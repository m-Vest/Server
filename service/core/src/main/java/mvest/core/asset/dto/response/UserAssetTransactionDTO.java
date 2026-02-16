package mvest.core.asset.dto.response;

import java.util.List;

public record UserAssetTransactionDTO(
        Long userId,
        List<UserAssetTransactionItemDTO> transactions
) {
}
