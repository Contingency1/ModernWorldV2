package kr.modernworld.modernworldv2.user.infrastructure.auth.naver;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NaverUserInfoDTO(
    @JsonProperty("resultcode")
    String resultCode,
    String message,
    NaverUserInfoResponseDTO response
) {


}
