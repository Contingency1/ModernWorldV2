package kr.modernworld.modernworldv2.user.infrastructure.auth.naver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public record NaverUserInfoSuccessDTO(
    @JsonProperty("resultcode")
    String resultCode,
    String message,
    NaverUserInfoResponseDTO response
) {

  public NaverUserInfoSuccessDTO {
    Objects.requireNonNull(resultCode, "resultCode");
    Objects.requireNonNull(message, "message");
    Objects.requireNonNull(response, "response");
  }
}
