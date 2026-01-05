package kr.modernworld.modernworldv2.asset.presentation.present.dto.res;

import java.time.Instant;

public record PresentResponseDTO(
    Long no,
    Long itemNo,
    Long senderNo,
    Long receiverNo,
    Instant createdAt,
    String status
) {

}
