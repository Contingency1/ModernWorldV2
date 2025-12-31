package kr.modernworld.modernworldv2.member.infrastructure.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.jwt")
public record JwtProperties(
    String accessSecret,
    String refreshSecret,
    Long accessExpiration,
    Long refreshExpiration,
    String issuer
) {

}
