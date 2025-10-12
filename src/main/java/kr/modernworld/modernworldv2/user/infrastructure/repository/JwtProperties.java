package kr.modernworld.modernworldv2.user.infrastructure.repository;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.jwt")
public record JwtProperties(
    String accessSecret,
    String refreshSecret,
    long accessExpiration,
    long refreshExpiration,
    String issuer
) {

}
