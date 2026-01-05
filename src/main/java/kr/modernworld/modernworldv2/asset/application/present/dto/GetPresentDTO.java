package kr.modernworld.modernworldv2.asset.application.present.dto;

import java.time.Instant;
import kr.modernworld.modernworldv2.asset.domain.present.PresentStatus;

public record GetPresentDTO(
    Long no,
    PresentStatus status,
    Instant createdAt,
    PresentItemDTO item,
    PresentUserDTO userPresentSenderNo,
    PresentUserDTO userPresentReceiverNo
) {

}
