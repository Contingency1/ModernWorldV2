package kr.modernworld.modernworldv2.user.presentation.present.dto.res;

import java.time.Instant;
import kr.modernworld.modernworldv2.user.domain.present.PresentStatus;

public record PresentResponseDTO(
    Long no,
    Long itemNo,
    Long senderNo,
    Long receiverNo,
    Instant createdAt,
    PresentStatus status
) {

}
