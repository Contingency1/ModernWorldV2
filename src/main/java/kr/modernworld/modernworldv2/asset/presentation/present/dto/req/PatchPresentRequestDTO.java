package kr.modernworld.modernworldv2.asset.presentation.present.dto.req;

import jakarta.validation.constraints.NotNull;
import kr.modernworld.modernworldv2.asset.application.present.HandlePresentStatus;

public record PatchPresentRequestDTO(
    @NotNull
    HandlePresentStatus status
) {

}
