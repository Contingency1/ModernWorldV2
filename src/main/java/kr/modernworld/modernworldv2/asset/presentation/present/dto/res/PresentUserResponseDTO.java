package kr.modernworld.modernworldv2.asset.presentation.present.dto.res;

import kr.modernworld.modernworldv2.asset.application.present.dto.PresentUserDTO;

public record PresentUserResponseDTO(
    Long no,
    String nickname
) {

  public static PresentUserResponseDTO from(PresentUserDTO user) {
    return new PresentUserResponseDTO(
        user.no(),
        user.nickname()
    );
  }
}
