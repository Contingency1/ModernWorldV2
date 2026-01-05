package kr.modernworld.modernworldv2.social.presentation.like.dto.res;

import kr.modernworld.modernworldv2.social.application.like.dto.LikeUserDTO;

public record LikeUserResponseDTO(
    Long no,
    String nickname
) {

  public static LikeUserResponseDTO from(LikeUserDTO dto) {
    return new LikeUserResponseDTO(dto.no(), dto.nickname());
  }

}
