package kr.modernworld.modernworldv2.social.application.comment.dto;

public record CommentUserDTO(
    Long no,
    String nickname
) {

  public CommentUserDTO {
    if (no == null) {
      no = 0L;
    }

    if (nickname == null) {
      nickname = "삭제된 사용자";
    }
  }
}
