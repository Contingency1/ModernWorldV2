package kr.modernworld.modernworldv2.user.infrastructure.auth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public record KakaoUserInfoDTO(
    Long id,

    @JsonProperty("has_signed_up")
    Optional<Boolean> hasSignedUp,

    @JsonProperty("connected_at")
    Optional<Instant> connectedAt,

    @JsonProperty("synched_at")
    Optional<Instant> synchedAt,

    KakaoUserDetailsDTO properties,

    @JsonProperty("kakao_account")
    Optional<KakaoAccountDTO> kakaoAccount,

    @JsonProperty("for_partner")
    Optional<KakaoForPartnerDTO> forPartner
) {

  public KakaoUserInfoDTO {
    Objects.requireNonNull(id);
    Objects.requireNonNull(properties);
  }
}
