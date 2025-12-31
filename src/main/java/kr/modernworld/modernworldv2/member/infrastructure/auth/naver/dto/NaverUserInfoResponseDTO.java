package kr.modernworld.modernworldv2.member.infrastructure.auth.naver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import java.util.Optional;

public record NaverUserInfoResponseDTO(
    String id,
    Optional<String> nickname,
    String name,
    Optional<String> email,
    Optional<String> gender,
    Optional<String> age,
    Optional<String> birthday,
    @JsonProperty("profile_image")
    String profileImage,
    Optional<String> birthyear,
    Optional<String> mobile
) {

  public NaverUserInfoResponseDTO {
    Objects.requireNonNull(id, "id cannot be null");
//    Objects.requireNonNull(nickname);
    Objects.requireNonNull(name, "name cannot be null");
//    Objects.requireNonNull(email);
//    Objects.requireNonNull(gender);
//    Objects.requireNonNull(age);
//    Objects.requireNonNull(birthday);
    Objects.requireNonNull(profileImage, "profileImage cannot be null");
//    Objects.requireNonNull(birthyear);
//    Objects.requireNonNull(mobile);
  }
}