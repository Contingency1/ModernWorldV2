package kr.modernworld.modernworldv2.social.presentation.comment.dto.res;

import java.time.Instant;

public record CommentResponseDTO(
    Long no,
    String content,
    Instant createdAt,
    CommentUserDTO commentSender,
    CommentUserDTO commentReceiver
) {

}
