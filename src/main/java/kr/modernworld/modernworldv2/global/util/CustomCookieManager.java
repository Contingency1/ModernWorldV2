package kr.modernworld.modernworldv2.global.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseCookie.ResponseCookieBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomCookieManager {

  private final RefreshCookieProperties refreshProps;
  private final StateCookieProperties stateProps;

  public ResponseCookie createRefreshTokenCookie(String refreshToken, long expiredAt) {
    long ttlSeconds = (expiredAt - System.currentTimeMillis()) / 1000;
    ResponseCookieBuilder cookie = setRefreshCookie(refreshToken);

    if (ttlSeconds > 0) {
      cookie.maxAge(ttlSeconds);
    } else {
      cookie.maxAge(0);
    }

    return cookie.build();
  }

  public ResponseCookie createStateCookie(String state, long expiredAt) {
    long ttlSeconds = (expiredAt - System.currentTimeMillis()) / 1000;
    ResponseCookieBuilder cookie = setStateCookie(state);

    if (ttlSeconds > 0) {
      cookie.maxAge(ttlSeconds);
    } else {
      cookie.maxAge(0);
    }

    return cookie.build();
  }

  public ResponseCookie invalidateRefreshTokenCookie() {
    ResponseCookieBuilder cookie = setRefreshCookie(null);

    return cookie.maxAge(0).build();
  }

  public ResponseCookie invalidateStateCookie() {
    ResponseCookieBuilder cookie = setStateCookie(null);

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

  private ResponseCookieBuilder setStateCookie(String state) {
    return ResponseCookie
        .from(stateProps.name(), state == null ? "" : state)
        .httpOnly(stateProps.httpOnly())
        .secure(stateProps.secure())
        .path(stateProps.path())
        .domain(stateProps.domain())
        .sameSite(stateProps.sameSite());
  }

}
