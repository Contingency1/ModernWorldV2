package kr.modernworld.modernworldv2.user.infrastructure.auth.naver;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NaverTokenDTO(

    @JsonProperty("access_token")
    String accessToken,

    @JsonProperty("refresh_token")
    String refreshToken,

    @JsonProperty("token_type")
    String tokenType,

    @JsonProperty("expires_in")
    Integer expiresIn,

    String error,

    @JsonProperty("error_description")
    String errorDescription
) {

}