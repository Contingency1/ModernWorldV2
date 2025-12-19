package kr.modernworld.modernworldv2.user.presentation.reply.dto.res;

import java.time.Instant;

public record ReplyResponseDTO(
    Long no,
    Long commentNo,
    String content,
    Instant createdAt,
    ReplyUserDTO user
) {

}
