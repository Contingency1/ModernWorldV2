package kr.modernworld.modernworldv2.user.presentation.neighbor.dto.res;

import java.time.Instant;

public record NeighborResponseDTO(
    Long no,
    NeighborUserInfoDTO neighborSenderNo,
    NeighborUserInfoDTO neighborReceiverNo,
    Instant createdAt,
    Boolean status
) {

}
