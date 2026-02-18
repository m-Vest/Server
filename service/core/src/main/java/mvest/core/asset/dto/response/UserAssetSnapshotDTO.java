package mvest.core.asset.dto.response;

import java.util.List;

public record UserAssetSnapshotDTO(
        Long userId,
        List<AssetDailySnapshotDTO> snapshots
) {
}
