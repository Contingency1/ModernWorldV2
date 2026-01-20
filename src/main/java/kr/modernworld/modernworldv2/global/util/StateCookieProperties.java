package kr.modernworld.modernworldv2.global.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cookie.login-state")
public record StateCookieProperties(
    String name,
    Boolean httpOnly,
    Boolean secure,
    String sameSite,
    String path,
    String domain
) {

}
