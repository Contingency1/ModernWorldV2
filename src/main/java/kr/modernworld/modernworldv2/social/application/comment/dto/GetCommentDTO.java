package kr.modernworld.modernworldv2.social.application.comment.dto;

import java.time.Instant;

public record GetCommentDTO(
    Long no,
    String content,
    Instant createdAt,
    CommentUserDTO commentSender,
    CommentUserDTO commentReceiver,
    GetCommentReplyCountDTO count
) {

}
