package kr.modernworld.modernworldv2.user.infrastructure.auth.google.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;

public record GoogleTokenFailDTO(
    Optional<String> error,

    @JsonProperty("error_description")
    Optional<String> errorDescription
) {

}
