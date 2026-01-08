package kr.modernworld.modernworldv2.member.presentation.user.dto.res;

import kr.modernworld.modernworldv2.member.application.user.dto.UserNicknameDTO;

public record UserNicknameResponseDTO(
    Long no,
    String nickname
) {

  public static UserNicknameResponseDTO from(UserNicknameDTO dto) {
    return new UserNicknameResponseDTO(dto.userNo(), dto.nickname());
  }
}
