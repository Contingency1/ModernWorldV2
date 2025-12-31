package kr.modernworld.modernworldv2.member.infrastructure.auth.naver;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "naver.client")
public record NaverOAuthProperties(
    String id,
    String secret,
    String callbackUrl
) {

}
