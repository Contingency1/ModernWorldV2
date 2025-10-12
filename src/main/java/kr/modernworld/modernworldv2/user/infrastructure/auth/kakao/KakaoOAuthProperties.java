package kr.modernworld.modernworldv2.user.infrastructure.auth.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao.client")
public record KakaoOAuthProperties(
    String id,
    String secret,
    String callbackUrl
) {

}
