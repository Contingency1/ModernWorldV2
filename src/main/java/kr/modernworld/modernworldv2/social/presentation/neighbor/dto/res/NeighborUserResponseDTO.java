package kr.modernworld.modernworldv2.social.presentation.neighbor.dto.res;

import kr.modernworld.modernworldv2.social.application.neighbor.dto.NeighborUserDTO;

public record NeighborUserResponseDTO(
    Long no,
    String nickname
) {

  public static NeighborUserResponseDTO from(NeighborUserDTO dto) {
    return new NeighborUserResponseDTO(dto.no(), dto.nickname());
  }

}
