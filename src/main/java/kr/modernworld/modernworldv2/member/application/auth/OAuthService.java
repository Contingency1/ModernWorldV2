package kr.modernworld.modernworldv2.member.application.auth;

import java.util.UUID;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.member.application.auth.dto.LoginResultDTO;
import kr.modernworld.modernworldv2.member.application.auth.dto.RenewRefreshTokenDTO;
import kr.modernworld.modernworldv2.member.application.auth.event.LoginFailEvent;
import kr.modernworld.modernworldv2.member.application.auth.event.LoginSuccessEvent;
import kr.modernworld.modernworldv2.member.application.user.UserService;
import kr.modernworld.modernworldv2.member.application.user.socialtoken.SocialTokenService;
import kr.modernworld.modernworldv2.member.domain.auth.port.OAuthClient;
import kr.modernworld.modernworldv2.member.domain.auth.port.SessionRepository;
import kr.modernworld.modernworldv2.member.domain.auth.port.TokenProvider;
import kr.modernworld.modernworldv2.member.domain.token.RefreshTokenRepository;
import kr.modernworld.modernworldv2.member.domain.user.User;
import kr.modernworld.modernworldv2.member.domain.user.UserDomain;
import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenResultDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenUserInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService {

  private final SessionRepository sessionRepository;
  private final OAuthProvider authProvider;
  private final ApplicationEventPublisher eventPublisher;

  private final TokenProvider tokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserService userService;
  private final SocialTokenService socialTokenService;
  private final String sessionKey = "SESSION_KEY";

  public String buildLoginUrl(UserDomain providerName) {
    OAuthClient client = authProvider.getOAuthClient(providerName);

    String state = UUID.randomUUID().toString();
    sessionRepository.save(sessionKey, state);

    return client.getLoginUrl(state);
  }

  public LoginResultDTO login(UserDomain providerName, String authCode, String state) {
    String storedState = sessionRepository.findValue(sessionKey);

    // 테스트 할때는 아래 state값 확인로직 주석처리할것.
    if (!storedState.equals(state)) {
      eventPublisher.publishEvent(new LoginFailEvent(this));
      throw new BusinessException(BusinessErrorCode.INVALID_OAUTH_STATE);
    }

    OAuthClient client = authProvider.getOAuthClient(providerName);

    OAuthTokenDTO socialToken = client.getSocialToken(state, authCode);
    SocialUserInfoDTO socialUserInfo = client.getSocialUserInfo(socialToken.socialAccessToken());

    User savedUser = userService.save(socialUserInfo, providerName, socialToken);

    Long now = System.currentTimeMillis();

    TokenResultDTO accessToken = tokenProvider.createAccess(savedUser.getNo(), savedUser.getAdmin(),
        now);
    TokenResultDTO refreshToken = tokenProvider.createRefresh(savedUser.getNo(),
        savedUser.getAdmin(), now);

    refreshTokenRepository.save(savedUser.getNo(), refreshToken.token(),
        refreshToken.expirationMillis());

    eventPublisher.publishEvent(new LoginSuccessEvent(this));

    return new LoginResultDTO(
        accessToken.token(),
        refreshToken.token(),
        savedUser.getNickname(),
        savedUser.getNo(),
        accessToken.expirationMillis(),
        refreshToken.expirationMillis());
  }

  public RenewRefreshTokenDTO renewToken(String inputToken) {
    TokenUserInfoDTO user = tokenProvider.validateRefresh(inputToken);

    if (!refreshTokenRepository.tryLock(user.userNo())) {
      throw new BusinessException(BusinessErrorCode.REDIS_CONCURRENT_UPDATE_REQUEST);
    }

    try {
      String savedRefreshToken =
          refreshTokenRepository.findByUserNo(user.userNo())
              .orElseThrow(() -> new BusinessException(BusinessErrorCode.INVALID_REFRESH_TOKEN));

      if (!savedRefreshToken.equals(inputToken)) {
        refreshTokenRepository.delete(user.userNo());
        log.warn("Refresh Token Reuse Detected! UserNo: {}", user.userNo());

        throw new BusinessException(BusinessErrorCode.INVALID_REFRESH_TOKEN);
      }

      refreshTokenRepository.delete(user.userNo());

      Long now = System.currentTimeMillis();

      TokenResultDTO access = tokenProvider.createAccess(user.userNo(), user.isAdmin(), now);
      TokenResultDTO refresh = tokenProvider.createRefresh(user.userNo(), user.isAdmin(),
          now);

      refreshTokenRepository.save(user.userNo(), refresh.token(), refresh.expirationMillis());

      return new RenewRefreshTokenDTO(access.token(), refresh.token(), refresh.expirationMillis());
    } finally {
      refreshTokenRepository.unlock(user.userNo());
    }
  }

  public String logout(Long userNo) {
    refreshTokenRepository.delete(userNo);

    return "Logout Success.";
  }

  @Transactional
  public String unlink(Long userNo) {
    refreshTokenRepository.delete(userNo);
    userService.updateDeletedAt(userNo);

    UserDomain domain = userService.getUserDomain(userNo);
    OAuthClient client = authProvider.getOAuthClient(domain);

    String accessToken = socialTokenService.getSocialAccessToken(userNo);

    client.unlink(accessToken);

    return "Unlink Success.";
  }

  public String updateUserSocialImage(Long userNo) {
    UserDomain domain = userService.getUserDomain(userNo);
    String accessToken = socialTokenService.getSocialAccessToken(userNo);

    OAuthClient client = authProvider.getOAuthClient(domain);
    String socialImage = client.getSocialImage(accessToken);

    userService.updateSocialImage(userNo, socialImage);

    return socialImage;
  }
}
