package kr.modernworld.modernworldv2.member.infrastructure.auth.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao.client")
public record KakaoOAuthProperties(
    String id,
    String secret,
    String callbackUrl
) {

}
