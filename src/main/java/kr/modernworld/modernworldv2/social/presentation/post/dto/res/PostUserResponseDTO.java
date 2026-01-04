package kr.modernworld.modernworldv2.social.presentation.post.dto.res;

import kr.modernworld.modernworldv2.social.application.post.dto.PostUserDTO;

public record PostUserResponseDTO(
    Long no,
    String nickname
) {

  public static PostUserResponseDTO from(PostUserDTO dto) {
    return new PostUserResponseDTO(dto.no(), dto.nickname());
  }

}
