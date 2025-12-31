package kr.modernworld.modernworldv2.member.infrastructure.auth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;

public record KakaoAccountDTO(
    @JsonProperty("profile_needs_agreement")
    Optional<Boolean> profileNeedsAgreement,

    @JsonProperty("profile_nickname_needs_agreement")
    Optional<Boolean> profileNicknameNeedsAgreement,

    @JsonProperty("profile_image_needs_agreement")
    Optional<Boolean> profileImageNeedsAgreement,
    Optional<Profile> profile,

    @JsonProperty("name_needs_agreement")
    Optional<Boolean> nameNeedsAgreement,
    Optional<String> name,

    @JsonProperty("email_needs_agreement")
    Optional<Boolean> emailNeedsAgreement,

    @JsonProperty("is_email_valid")
    Optional<Boolean> isEmailValid,

    @JsonProperty("is_email_verified")
    Optional<Boolean> isEmailVerified,
    Optional<String> email,

    @JsonProperty("age_range_needs_agreement")
    Optional<Boolean> ageRangeNeedsAgreement,

    @JsonProperty("age_range") Optional<String> ageRange,

    @JsonProperty("birthday_needs_agreement")
    Optional<Boolean> birthdayNeedsAgreement,

    Optional<String> birthday,

    @JsonProperty("birthday_type")
    Optional<String> birthdayType,

    @JsonProperty("gender_needs_agreement")
    Optional<Boolean> genderNeedsAgreement,

    Optional<String> gender,

    @JsonProperty("phone_number_needs_agreement")
    Optional<Boolean> phoneNumberNeedsAgreement,

    @JsonProperty("phone_number")
    Optional<String> phoneNumber,

    @JsonProperty("ci_needs_agreement")
    Optional<Boolean> ciNeedsAgreement,

    Optional<String> ci,

    @JsonProperty("ci_authenticated_at")
    Optional<String> ciAuthenticatedAt
) {

  public record Profile(
      @JsonProperty("nickname")
      Optional<String> nickname,

      @JsonProperty("thumbnail_image_url")
      Optional<String> thumbnailImageUrl,

      @JsonProperty("profile_image_url")
      Optional<String> profileImageUrl,

      @JsonProperty("is_default_image")
      Optional<Boolean> isDefaultImage,

      @JsonProperty("is_default_nickname")
      Optional<String> isDefaultNickname
  ) {

  }
}
