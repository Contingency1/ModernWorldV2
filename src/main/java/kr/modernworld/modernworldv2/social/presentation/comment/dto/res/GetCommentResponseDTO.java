package kr.modernworld.modernworldv2.social.presentation.comment.dto.res;

import java.time.Instant;
import kr.modernworld.modernworldv2.social.application.comment.dto.GetCommentDTO;

public record GetCommentResponseDTO(
    Long no,
    String content,
    Instant createdAt,
    CommentUserResponseDTO commentSender,
    CommentUserResponseDTO commentReceiver,
    GetCommentReplyCountResponseDTO _count
) {

  public static GetCommentResponseDTO from(GetCommentDTO dto) {
    return new GetCommentResponseDTO(
        dto.no(),
        dto.content(),
        dto.createdAt(),
        CommentUserResponseDTO.from(dto.commentSender()),
        CommentUserResponseDTO.from(dto.commentReceiver()),
        GetCommentReplyCountResponseDTO.from(dto.count())
    );
  }
}
