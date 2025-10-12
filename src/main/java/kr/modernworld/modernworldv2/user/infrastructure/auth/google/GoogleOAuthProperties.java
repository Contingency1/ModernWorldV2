package kr.modernworld.modernworldv2.user.infrastructure.auth.google;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.client")
public record GoogleOAuthProperties(
    String id,
    String secret,
    String callbackUrl
) {

}
