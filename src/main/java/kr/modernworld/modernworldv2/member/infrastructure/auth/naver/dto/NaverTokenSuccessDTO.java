package kr.modernworld.modernworldv2.member.infrastructure.auth.naver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public record NaverTokenSuccessDTO(

    @JsonProperty("access_token")
    String accessToken,

    @JsonProperty("refresh_token")
    String refreshToken,

    @JsonProperty("token_type")
    String tokenType,

    @JsonProperty("expires_in")
    Integer expiresIn
) {

  public NaverTokenSuccessDTO {
    Objects.requireNonNull(accessToken, "accessToken cannot be null");
    Objects.requireNonNull(refreshToken, "refreshToken cannot be null");
    Objects.requireNonNull(tokenType, "tokenType cannot be null");
    Objects.requireNonNull(expiresIn, "expiresIn cannot be null");
  }
}