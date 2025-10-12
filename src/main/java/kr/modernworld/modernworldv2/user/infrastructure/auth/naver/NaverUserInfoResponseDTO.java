package kr.modernworld.modernworldv2.user.infrastructure.auth.naver;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NaverUserInfoResponseDTO(
    String id,
    String nickname,
    String name,
    String email,
    String gender,
    String age,
    String birthday,
    @JsonProperty("profile_image")
    String profileImage,
    String birthyear,
    String mobile
) {

}