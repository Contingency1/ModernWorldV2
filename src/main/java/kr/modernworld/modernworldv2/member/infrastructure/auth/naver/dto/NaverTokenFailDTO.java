package kr.modernworld.modernworldv2.member.infrastructure.auth.naver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;

public record NaverTokenFailDTO(
    Optional<String> error,

    @JsonProperty("error_description")
    Optional<String> errorDescription
) {

}