package kr.modernworld.modernworldv2.global.util;

import jakarta.servlet.http.Cookie;

public final class CookieUtil {

  private CookieUtil() {
  }

  public static Cookie createRefreshTokenCookie(String refreshToken, long expiredAt) {
    long ttlSeconds = (expiredAt - System.currentTimeMillis()) / 1000;

    Cookie cookie = new Cookie("refreshToken", refreshToken);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");

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
}
