package kr.modernworld.modernworldv2.social.presentation.reply.dto.res;

import kr.modernworld.modernworldv2.social.application.reply.dto.ReplyUserDTO;

public record ReplyUserResponseDTO(
    Long no,
    String nickname
) {

  public static ReplyUserResponseDTO from(ReplyUserDTO dto) {
    return new ReplyUserResponseDTO(dto.no(), dto.nickname());
  }

}
