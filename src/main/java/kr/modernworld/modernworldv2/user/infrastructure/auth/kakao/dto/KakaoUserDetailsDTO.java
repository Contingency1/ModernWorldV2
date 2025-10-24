package kr.modernworld.modernworldv2.user.infrastructure.auth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public record KakaoUserDetailsDTO(
    @JsonProperty("nickname")
    String nickname,

    @JsonProperty("profile_image")
    String profileImage
) {

  public KakaoUserDetailsDTO {
    Objects.requireNonNull(nickname, "nickname must not be null");
    Objects.requireNonNull(profileImage, "profileImage must not be null");
  }
}
