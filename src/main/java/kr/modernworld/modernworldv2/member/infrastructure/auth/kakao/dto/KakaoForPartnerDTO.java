package kr.modernworld.modernworldv2.member.infrastructure.auth.kakao.dto;

import java.util.Optional;

public record KakaoForPartnerDTO(
    Optional<String> uuid
) {

}
