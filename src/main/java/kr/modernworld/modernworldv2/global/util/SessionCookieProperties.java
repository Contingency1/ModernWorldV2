package kr.modernworld.modernworldv2.global.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cookie.session")
public record SessionCookieProperties(
    String name,
    Boolean httpOnly,
    Boolean secure,
    String sameSite,
    String path,
    String domain
) {

}
