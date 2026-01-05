package kr.modernworld.modernworldv2.social.application.reply.dto;

import java.time.Instant;

public record ReplyDTO(
    Long no,
    Long commentNo,
    String content,
    Instant createdAt,
    ReplyUserDTO user
) {

}
