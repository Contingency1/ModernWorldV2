package kr.modernworld.modernworldv2.user.infrastructure.auth.google.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import java.util.Optional;

public record GoogleTokenSuccessDTO(
    @JsonProperty("access_token")
    String accessToken,

    @JsonProperty("expires_in")
    Integer expiresIn,

    @JsonProperty("refresh_token")
    String refreshToken,

    @JsonProperty("refresh_token_expires_in")
    Optional<Integer> refreshTokenExpiresIn,

    @JsonProperty("scope")
    String scope,

    @JsonProperty("token_type")
    String tokenType
) {

  public GoogleTokenSuccessDTO {
    Objects.requireNonNull(accessToken, "accessToken may not be null");
    Objects.requireNonNull(expiresIn, "expiresIn may not be null");
    Objects.requireNonNull(refreshToken, "refreshToken may not be null");
    Objects.requireNonNull(scope, "scope may not be null");
    Objects.requireNonNull(tokenType, "tokenType may not be null");

  }
}
