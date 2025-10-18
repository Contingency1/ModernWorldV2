package kr.modernworld.modernworldv2.user.infrastructure.auth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;

public record KakaoTokenFailDTO(
    Optional<String> error,

    @JsonProperty("error_description")
    Optional<String> errorDescription
) {

}
