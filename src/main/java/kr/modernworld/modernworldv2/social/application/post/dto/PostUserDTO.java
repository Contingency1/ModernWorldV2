package kr.modernworld.modernworldv2.social.application.post.dto;

public record PostUserDTO(
    Long no,
    String nickname
) {

  public PostUserDTO {
    if (nickname == null) {
      nickname = "Deleted User";
    }

    if (no == null) {
      no = 0L;
    }
  }

}
