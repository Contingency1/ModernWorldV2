package kr.modernworld.modernworldv2.global.util;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomCookieManager {

  private final RefreshCookieProperties refreshProps;
  private final SessionCookieProperties sessionProps;

  public Cookie createRefreshTokenCookie(String refreshToken, long expiredAt) {
    long ttlSeconds = (expiredAt - System.currentTimeMillis()) / 1000;

    Cookie cookie = setRefreshCookie(refreshToken);

    if (ttlSeconds > 0) {
      if (ttlSeconds > Integer.MAX_VALUE) {
        cookie.setMaxAge(Integer.MAX_VALUE);
      } else {
        cookie.setMaxAge((int) ttlSeconds);
      }
    } else {
      cookie.setMaxAge(0);
    }

    return cookie;
  }

  public Cookie invalidateRefreshTokenCookie() {
    Cookie cookie = setRefreshCookie(null);

    cookie.setMaxAge(0);
    return cookie;
  }

  private Cookie setRefreshCookie(String refreshToken) {
    Cookie cookie = new Cookie(refreshProps.name(), refreshToken);
    cookie.setHttpOnly(refreshProps.httpOnly());
    cookie.setSecure(refreshProps.secure());
    cookie.setPath(refreshProps.path());
    cookie.setDomain(refreshProps.domain());

    return cookie;
  }

  private Cookie setSessionCookie(String refreshToken) {
    Cookie cookie = new Cookie(sessionProps.name(), refreshToken);
    cookie.setHttpOnly(sessionProps.httpOnly());
    cookie.setSecure(sessionProps.secure());
    cookie.setPath(sessionProps.path());
    cookie.setDomain(sessionProps.domain());

    return cookie;
  }

}
