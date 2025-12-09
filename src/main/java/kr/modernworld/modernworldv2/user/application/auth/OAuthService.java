package kr.modernworld.modernworldv2.user.application.auth;

import java.util.UUID;
import kr.modernworld.modernworldv2.user.application.auth.event.LoginFailEvent;
import kr.modernworld.modernworldv2.user.application.auth.event.LoginSuccessEvent;
import kr.modernworld.modernworldv2.user.application.user.UserService;
import kr.modernworld.modernworldv2.user.domain.auth.port.OAuthClient;
import kr.modernworld.modernworldv2.user.domain.auth.port.SessionRepository;
import kr.modernworld.modernworldv2.user.domain.auth.port.TokenProvider;
import kr.modernworld.modernworldv2.user.domain.token.RefreshTokenRepository;
import kr.modernworld.modernworldv2.user.domain.user.User;
import kr.modernworld.modernworldv2.user.domain.user.UserDomain;
import kr.modernworld.modernworldv2.user.infrastructure.auth.jwt.TokenResultDTO;
import kr.modernworld.modernworldv2.user.presentation.oauth.LoginResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthService {

  private final SessionRepository sessionRepository;
  private final OAuthProvider authProvider;
  private final ApplicationEventPublisher eventPublisher;

  private final TokenProvider tokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserService userService;

  public String buildLoginUrl(UserDomain providerName) {

    OAuthClient client = authProvider.getOAuthClient(providerName);

    String state = UUID.randomUUID().toString();
    sessionRepository.save("SESSION_KEY", state);

    return client.getLoginUrl(state);
  }

  public LoginResultDTO login(UserDomain providerName, String authCode, String state) {
    String storedSate = sessionRepository.findValue("SESSION_KEY");

    // 테스트 할때는 아래 state값 확인로직 주석처리할것.
    if (!storedSate.equals(state)) {
      eventPublisher.publishEvent(new LoginFailEvent(this));
      throw new RuntimeException("Login failed");
    }

    OAuthClient client = authProvider.getOAuthClient(providerName);

    OAuthTokenDTO socialToken = client.getSocialToken(state, authCode);
    SocialUserInfoDTO socialUserInfo = client.getSocialUserInfo(socialToken.socialAccessToken());

    //--------------------------------------------------------------------------------------
    //밴 됐는지 확인하는 로직 추후에 추가할것.

    //--------------------------------------------------------------------------------------

    User savedUser = userService.toPersistentedUser(socialUserInfo, client,
        socialToken);

    Long now = System.currentTimeMillis();

    TokenResultDTO accessToken = tokenProvider.createAccess(savedUser.getNo(), savedUser.getAdmin(),
        now);
    TokenResultDTO refreshToken = tokenProvider.createRefresh(savedUser.getNo(),
        savedUser.getAdmin(), now);

    refreshTokenRepository.save(savedUser.getNo(), refreshToken.token(),
        refreshToken.expirationMillis());

    eventPublisher.publishEvent(new LoginSuccessEvent(this));

    return new LoginResultDTO(accessToken.token(), refreshToken.token(),
        savedUser.getSocialName(),
        savedUser.getNo(),
        accessToken.expirationMillis(),
        refreshToken.expirationMillis());
  }


}
