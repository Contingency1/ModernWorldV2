package kr.modernworld.modernworldv2.member.infrastructure.auth.google.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public record GoogleApiErrorDTO(
    @JsonProperty("error")
    GoogleErrorDetailsDTO error
) {

  public GoogleApiErrorDTO {
    Objects.requireNonNull(error);
  }
}
