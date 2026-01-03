package kr.modernworld.modernworldv2.social.presentation.comment.dto.res;

import kr.modernworld.modernworldv2.social.application.comment.dto.CommentUserDTO;

public record CommentUserResponseDTO(
    Long no,
    String nickname
) {

  public static CommentUserResponseDTO from(CommentUserDTO user) {
    return new CommentUserResponseDTO(
        user.no(),
        user.nickname()
    );
  }

}
