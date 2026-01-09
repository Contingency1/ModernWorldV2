package kr.modernworld.modernworldv2.member.presentation.user.dto.res;

import kr.modernworld.modernworldv2.member.application.user.dto.UserDescriptionDTO;

public record UserDescriptionResponseDTO(
    Long no,
    String description
) {

  public static UserDescriptionResponseDTO from(UserDescriptionDTO user) {
    return new UserDescriptionResponseDTO(user.userNo(), user.description());
  }

}
