package kr.modernworld.modernworldv2.member.presentation.oauth;

import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.global.util.CustomCookieManager;
import kr.modernworld.modernworldv2.member.application.auth.OAuthService;
import kr.modernworld.modernworldv2.member.application.auth.dto.BuilderLoginUrlDTO;
import kr.modernworld.modernworldv2.member.application.auth.dto.LoginResultDTO;
import kr.modernworld.modernworldv2.member.application.auth.dto.RenewRefreshTokenDTO;
import kr.modernworld.modernworldv2.member.domain.user.UserDomain;
import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenUserInfoDTO;
import kr.modernworld.modernworldv2.member.presentation.oauth.dto.ExitResponseDTO;
import kr.modernworld.modernworldv2.member.presentation.oauth.dto.LoginResponseDTO;
import kr.modernworld.modernworldv2.member.presentation.oauth.dto.LoginURLResponseDTO;
import kr.modernworld.modernworldv2.member.presentation.oauth.dto.RenewalAccessTokenResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
  private final CustomCookieManager cookieManager;

  @GetMapping("/login-url/{provider}")
  public ResponseEntity<LoginURLResponseDTO> getLoginUrl(@PathVariable String provider) {
    UserDomain providerName = getProviderName(provider);

    BuilderLoginUrlDTO response = authService.buildLoginUrl(providerName);

    ResponseCookie sessionCookie = cookieManager.createSessionCookie(response.sessionKey(),
        response.expiredAt());

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, sessionCookie.toString())
        .body(new LoginURLResponseDTO(response.url()));
  }

  @PostMapping("/login/{provider}")
  public ResponseEntity<LoginResponseDTO> login(
      @PathVariable String provider,
      @RequestParam String code,
      @RequestParam String state,
      @CookieValue(name = "${cookie.session.name}", required = false, defaultValue = "") String sessionKey) {
    UserDomain providerName = getProviderName(provider);

    LoginResultDTO response = authService.login(sessionKey, providerName, code, state);

    ResponseCookie refreshTokenCookie = cookieManager
        .createRefreshTokenCookie(response.refreshToken(), response.refreshExpirationMillis());
    ResponseCookie sessionCookie = cookieManager.invalidateSessionCookie();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
        .header(HttpHeaders.SET_COOKIE, sessionCookie.toString())
        .body(new LoginResponseDTO(response.accessToken(), response.nickname(), response.userNo()));
  }

  @GetMapping("/new-access-token")
  public ResponseEntity<RenewalAccessTokenResponseDTO> renewAccessToken(
      @CookieValue(name = "${cookie.refresh.name}", required = false, defaultValue = "") String refreshToken) {
    RenewRefreshTokenDTO response = authService.renewToken(refreshToken);

    ResponseCookie cookie = cookieManager.createRefreshTokenCookie(
        response.refreshToken(),
        response.refreshExpirationMillis());

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new RenewalAccessTokenResponseDTO(response.accessToken()));
  }

  @DeleteMapping("/logout")
  public ResponseEntity<ExitResponseDTO> logout(@AuthenticationPrincipal TokenUserInfoDTO user) {
    String response = authService.logout(user.userNo());

    ResponseCookie cookie = cookieManager.invalidateRefreshTokenCookie();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new ExitResponseDTO(response));
  }

  @DeleteMapping("/unlink")
  public ResponseEntity<ExitResponseDTO> unlink(@AuthenticationPrincipal TokenUserInfoDTO user) {
    String response = authService.unlink(user.userNo());

    ResponseCookie cookie = cookieManager.invalidateRefreshTokenCookie();

    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body(new ExitResponseDTO(response));
  }

  private UserDomain getProviderName(String provider) {
    UserDomain providerName;

    try {
      providerName = UserDomain.strToUserDomain(provider);
    } catch (IllegalArgumentException e) {
      throw new BusinessException(BusinessErrorCode.NOT_SUPPORTED_PROVIDER);
    }
    return providerName;
  }
}
