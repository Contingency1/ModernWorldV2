package kr.modernworld.modernworldv2.member.infrastructure.auth.naver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;

public record NaverUserInfoFailDTO(
    @JsonProperty("resultcode")
    Optional<String> resultCode,
    Optional<String> message
) {

}
