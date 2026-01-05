package kr.modernworld.modernworldv2.social.presentation.comment.dto.res;

import kr.modernworld.modernworldv2.social.application.comment.dto.GetCommentReplyCountDTO;

public record GetCommentReplyCountResponseDTO(
    Long reply
) {

  public static GetCommentReplyCountResponseDTO from(GetCommentReplyCountDTO dto) {
    return new GetCommentReplyCountResponseDTO(dto.reply());
  }
}
