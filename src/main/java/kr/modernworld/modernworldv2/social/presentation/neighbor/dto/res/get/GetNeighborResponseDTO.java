package kr.modernworld.modernworldv2.social.presentation.neighbor.dto.res.get;

import java.time.Instant;
import kr.modernworld.modernworldv2.social.application.neighbor.dto.get.GetNeighborDTO;

public record GetNeighborResponseDTO(
    Long no,
    Instant createdAt,
    Boolean status,
    GetNeighborUserResponseDTO neighbor
) {

  public static GetNeighborResponseDTO from(GetNeighborDTO dto) {
    return new GetNeighborResponseDTO(dto.no(), dto.createdAt(), dto.status(),
        GetNeighborUserResponseDTO.from(dto.neighbor()));

  }

}
