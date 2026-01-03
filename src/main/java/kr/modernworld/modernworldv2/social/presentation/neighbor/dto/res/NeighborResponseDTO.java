package kr.modernworld.modernworldv2.social.presentation.neighbor.dto.res;

import java.time.Instant;
import kr.modernworld.modernworldv2.social.application.neighbor.dto.NeighborDTO;

public record NeighborResponseDTO(
    Long no,
    NeighborUserResponseDTO neighborSenderNo,
    NeighborUserResponseDTO neighborReceiverNo,
    Instant createdAt,
    Boolean status
) {

  public static NeighborResponseDTO from(NeighborDTO neighbor) {
    return new NeighborResponseDTO(
        neighbor.no(),
        NeighborUserResponseDTO.from(neighbor.sender()),
        NeighborUserResponseDTO.from(neighbor.receiver()),
        neighbor.createdAt(),
        neighbor.status()
    );
  }

}
