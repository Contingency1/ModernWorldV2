package kr.modernworld.modernworldv2.social.application.neighbor.dto.get;

import java.time.Instant;

public record GetNeighborDTO(
    Long no,
    Instant createdAt,
    Boolean status,
    GetNeighborUserDTO neighbor
) {

}
