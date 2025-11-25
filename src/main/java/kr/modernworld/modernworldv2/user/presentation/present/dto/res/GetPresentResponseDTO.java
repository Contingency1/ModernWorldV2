package kr.modernworld.modernworldv2.user.presentation.present.dto.res;

import java.time.Instant;
import kr.modernworld.modernworldv2.user.domain.present.PresentStatus;

public record GetPresentResponseDTO(
    Long no,
    PresentStatus status,
    Instant createdAt,
    PresentItemDTO item,
    PresentUserDTO userPresentSenderNo,
    PresentUserDTO userPresentReceiverNo
) {

}
