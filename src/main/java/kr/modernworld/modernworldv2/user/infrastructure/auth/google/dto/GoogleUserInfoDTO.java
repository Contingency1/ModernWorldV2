package kr.modernworld.modernworldv2.user.infrastructure.auth.google.dto;

import java.util.Objects;

public record GoogleUserInfoDTO(
    String id,
    String name,
    String picture
) {

  public GoogleUserInfoDTO {
    Objects.requireNonNull(id, "id cannot be null");
    Objects.requireNonNull(name, "name cannot be null");
    Objects.requireNonNull(picture, "picture cannot be null");
  }
}
