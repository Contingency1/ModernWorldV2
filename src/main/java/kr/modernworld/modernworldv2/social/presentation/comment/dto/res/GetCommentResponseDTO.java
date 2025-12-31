package kr.modernworld.modernworldv2.social.presentation.comment.dto.res;

import java.time.Instant;

public record GetCommentResponseDTO(
    Long no,
    String content,
    Instant createdAt,
    CommentUserDTO commentSender,
    CommentUserDTO commentReceiver,
    GetCommentReplyCountDTO _count
) {

}
