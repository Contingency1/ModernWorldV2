package kr.modernworld.modernworldv2.user.presentation.neighbor.dto.res.get;

import java.time.Instant;

public record GetNeighborResponseDTO(
    Long no,
    Instant createdAt,
    Boolean status,
    NeighborUserDTO neighbor
) {

}
