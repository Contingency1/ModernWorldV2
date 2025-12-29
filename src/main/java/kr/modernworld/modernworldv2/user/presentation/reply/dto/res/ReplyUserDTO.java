package kr.modernworld.modernworldv2.user.presentation.reply.dto.res;

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
