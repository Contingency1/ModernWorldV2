package kr.modernworld.modernworldv2.user.infrastructure.auth.kakao.dto;

import java.util.Optional;

public record KakaoForPartnerDTO(
    Optional<String> uuid
) {

}
