package kr.modernworld.modernworldv2.asset.presentation.present.dto.res;

import java.time.Instant;
import kr.modernworld.modernworldv2.asset.domain.present.PresentStatus;

public record PresentResponseDTO(
    Long no,
    Long itemNo,
    Long senderNo,
    Long receiverNo,
    Instant createdAt,
    PresentStatus status
) {

}
