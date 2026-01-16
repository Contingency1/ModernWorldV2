package kr.modernworld.modernworldv2.member.presentation.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.modernworld.modernworldv2.global.util.CookieUtil;
import kr.modernworld.modernworldv2.member.application.auth.OAuthService;
import kr.modernworld.modernworldv2.member.application.auth.dto.LoginResultDTO;
import kr.modernworld.modernworldv2.member.application.auth.dto.RenewRefreshTokenDTO;
import kr.modernworld.modernworldv2.member.domain.user.UserDomain;
import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenUserInfoDTO;
import kr.modernworld.modernworldv2.member.presentation.oauth.dto.LoginResponseDTO;
import kr.modernworld.modernworldv2.member.presentation.oauth.dto.LoginURLResponseDTO;
import kr.modernworld.modernworldv2.member.presentation.oauth.dto.LogoutResponseDTO;
import kr.modernworld.modernworldv2.member.presentation.oauth.dto.RenewalAccessTokenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    Cookie cookie = CookieUtil.createRefreshTokenCookie(
        result.refreshToken(),
        result.refreshExpirationMillis());
    httpResponse.addCookie(cookie);

    return new ResponseEntity<>(
        new LoginResponseDTO(result.accessToken(), result.nickname(), result.userNo()),
        HttpStatus.OK);
  }

  @GetMapping("/new-access-token")
  public ResponseEntity<RenewalAccessTokenResponseDTO> renewAccessToken(
      @CookieValue("refreshToken") String inputCookie,
      HttpServletResponse httpResponse) {
    RenewRefreshTokenDTO response = authService.renewToken(inputCookie);

    Cookie cookie = CookieUtil.createRefreshTokenCookie(
        response.refreshToken(),
        response.refreshExpirationMillis());
    httpResponse.addCookie(cookie);

    return new ResponseEntity<>(
        new RenewalAccessTokenResponseDTO(response.accessToken()), HttpStatus.OK);
  }

  @DeleteMapping("/logout")
  public ResponseEntity<LogoutResponseDTO> logout(@AuthenticationPrincipal TokenUserInfoDTO user) {
    String response = authService.logout(user.userNo());

    return new ResponseEntity<>(new LogoutResponseDTO(response), HttpStatus.OK);
  }
}
