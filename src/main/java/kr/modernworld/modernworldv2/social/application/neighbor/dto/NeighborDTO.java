package kr.modernworld.modernworldv2.social.application.neighbor.dto;

import java.time.Instant;

public record NeighborDTO(
    Long no,
    NeighborUserDTO sender,
    NeighborUserDTO receiver,
    Instant createdAt,
    Boolean status
) {

}
