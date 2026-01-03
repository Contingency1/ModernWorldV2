package kr.modernworld.modernworldv2.social.presentation.comment.dto.res;

import java.time.Instant;
import kr.modernworld.modernworldv2.social.application.comment.dto.CommentDTO;

public record CommentResponseDTO(
    Long no,
    String content,
    Instant createdAt,
    CommentUserResponseDTO commentSender,
    CommentUserResponseDTO commentReceiver
) {

  public static CommentResponseDTO from(CommentDTO comment) {
    return new CommentResponseDTO(
        comment.no(), comment.content(), comment.createdAt(),
        CommentUserResponseDTO.from(comment.commentSender()),
        CommentUserResponseDTO.from(comment.commentReceiver())
    );
  }

}
