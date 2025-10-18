package kr.modernworld.modernworldv2.user.presentation.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.modernworld.modernworldv2.global.util.CookieUtil;
import kr.modernworld.modernworldv2.user.application.oauth.OAuthService;
import kr.modernworld.modernworldv2.user.domain.user.UserDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class OAuthController {

  private final OAuthService authService;

  @GetMapping("/login-url/{provider}")
  public ResponseEntity<LoginURLResponseDTO> getLoginUrl(@PathVariable UserDomain provider) {
    return new ResponseEntity<>(
        new LoginURLResponseDTO(authService.buildLoginUrl(provider)),
        HttpStatus.OK);
  }

  @PostMapping("/login/{provider}")
  public ResponseEntity<LoginResponseDTO> login(
      @PathVariable UserDomain provider,
      @RequestParam String code,
      @RequestParam String state,
      HttpServletResponse httpResponse
  ) {
    LoginResultDTO result = authService.login(provider, code, state);

    Cookie cookie = CookieUtil.createRefreshTokenCookie(result.refreshToken(),
        result.refreshExpirationMillis());
    httpResponse.addCookie(cookie);

    return new ResponseEntity<>(
        new LoginResponseDTO(result.accessToken(), result.nickname(), result.userNo()),
        HttpStatus.OK);
  }
}
