package mvest.core.asset.controller;

import lombok.RequiredArgsConstructor;
import mvest.core.asset.application.AssetService;
import mvest.core.asset.dto.response.UserAssetDTO;
import mvest.core.asset.dto.response.UserAssetTransactionDTO;
import mvest.core.global.code.CommonSuccessCode;
import mvest.core.global.dto.ResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/asset")
public class AssetController {

    private final AssetService assetService;

    @GetMapping
    public ResponseDTO<UserAssetDTO> getUserAsset(
            // @UserId Long userId
    ) {
        Long userId = 1L;
        return ResponseDTO.success(CommonSuccessCode.OK, assetService.getUserAsset(userId));
    }

    @GetMapping("/transactions")
    public ResponseDTO<UserAssetTransactionDTO> getUserAssetTransaction(
            // @UserId Long userId
    ) {
        Long userId = 1L;
        return ResponseDTO.success(CommonSuccessCode.OK, assetService.getUserAssetTransaction(userId));
    }
}
