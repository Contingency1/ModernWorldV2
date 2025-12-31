package kr.modernworld.modernworldv2.member.infrastructure.auth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import java.util.Optional;

public record KakaoTokenSuccessDTO(
    @JsonProperty("token_type")
    String tokenType,

    @JsonProperty("access_token")
    String accessToken,

    @JsonProperty("id_token")
    Optional<String> idToken,

    @JsonProperty("expires_in")
    Integer expiresIn,

    @JsonProperty("refresh_token")
    Optional<String> refreshToken,

    @JsonProperty("refresh_token_expires_in")
    Optional<Integer> refreshTokenExpiresIn,

    String scope
) {

  public KakaoTokenSuccessDTO {
    Objects.requireNonNull(tokenType);
    Objects.requireNonNull(accessToken);
    Objects.requireNonNull(expiresIn);
    Objects.requireNonNull(scope);
  }
}
