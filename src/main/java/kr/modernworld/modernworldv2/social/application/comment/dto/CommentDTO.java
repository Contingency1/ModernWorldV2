package kr.modernworld.modernworldv2.social.application.comment.dto;

import java.time.Instant;

public record CommentDTO(
    Long no,
    String content,
    Instant createdAt,
    CommentUserDTO commentSender,
    CommentUserDTO commentReceiver
) {

}
