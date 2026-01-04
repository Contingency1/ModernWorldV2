package kr.modernworld.modernworldv2.social.presentation.reply.dto.res;

import java.time.Instant;
import kr.modernworld.modernworldv2.social.application.reply.dto.ReplyDTO;

public record ReplyResponseDTO(
    Long no,
    Long commentNo,
    String content,
    Instant createdAt,
    ReplyUserResponseDTO user
) {

  public static ReplyResponseDTO from(ReplyDTO dto) {
    return new ReplyResponseDTO(
        dto.no(),
        dto.commentNo(),
        dto.content(),
        dto.createdAt(),
        ReplyUserResponseDTO.from(dto.user())
    );
  }
}
