package kr.modernworld.modernworldv2.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomCookieManager {

  private final RefreshCookieProperties refreshProps;
  private final SessionCookieProperties sessionProps;

  public ResponseCookie createRefreshTokenCookie(String refreshToken, long expiredAt) {
    long ttlSeconds = (expiredAt - System.currentTimeMillis()) / 1000;

    ResponseCookieBuilder cookie = setRefreshCookie(refreshToken);

    if (ttlSeconds > 0) {
      if (ttlSeconds > Integer.MAX_VALUE) {
        cookie.maxAge(Integer.MAX_VALUE);
      } else {
        cookie.maxAge((int) ttlSeconds);
      }
    } else {
      cookie.maxAge(0);
    }

    return cookie.build();
  }

  public ResponseCookie invalidateRefreshTokenCookie() {
    ResponseCookieBuilder cookie = setRefreshCookie(null);

    return cookie.maxAge(0).build();
  }

  public ResponseCookie invalidateSessionCookie() {
    ResponseCookieBuilder cookie = setSessionCookie(null);

    return cookie.maxAge(0).build();
  }

  private ResponseCookieBuilder setRefreshCookie(String refreshToken) {

    return ResponseCookie
        .from(refreshProps.name(), refreshToken == null ? "" : refreshToken)
        .httpOnly(refreshProps.httpOnly())
        .secure(refreshProps.secure())
        .path(refreshProps.path())
        .domain(refreshProps.domain())
        .sameSite(refreshProps.sameSite());
  }

  private ResponseCookieBuilder setSessionCookie(String key) {
    return ResponseCookie
        .from(sessionProps.name(), key)
        .httpOnly(sessionProps.httpOnly())
        .secure(sessionProps.secure())
        .path(sessionProps.path())
        .domain(sessionProps.domain())
        .sameSite(sessionProps.sameSite());
  }

}
