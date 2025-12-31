package kr.modernworld.modernworldv2.social.presentation.post.dto.res;

public record PostUserInfoDTO(
    Long no,
    String nickname
) {

  public PostUserInfoDTO {
    if (nickname == null) {
      nickname = "Deleted User";
    }

    if (no == null) {
      no = 0L;
    }
  }
}
