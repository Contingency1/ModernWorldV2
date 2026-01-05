package kr.modernworld.modernworldv2.social.application.reply.dto;

public record ReplyUserDTO(
    Long no,
    String nickname
) {

  public ReplyUserDTO {
    if (no == null) {
      no = 0L;
    }

    if (nickname == null) {
      nickname = "삭제된 사용자";
    }
  }
}
