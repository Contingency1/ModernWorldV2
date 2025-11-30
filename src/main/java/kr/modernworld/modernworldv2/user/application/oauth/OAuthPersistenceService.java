package kr.modernworld.modernworldv2.user.application.oauth;

import kr.modernworld.modernworldv2.user.application.LegendService;
import kr.modernworld.modernworldv2.user.domain.port.OAuthClient;
import kr.modernworld.modernworldv2.user.domain.port.user.UserQueryRepository;
import kr.modernworld.modernworldv2.user.domain.port.user.UserRepository;
import kr.modernworld.modernworldv2.user.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OAuthPersistenceService {

  private final UserRepository userRepository;
  private final UserQueryRepository userQueryRepository;
  private final LegendService legendService;

  @Transactional
  public User toPersistentedUser(SocialUserInfoDTO socialUserInfo, OAuthClient client,
      OAuthTokenDTO socialToken) {
    User user = userQueryRepository.findByUniqueIdentifier(socialUserInfo.uniqueIdentifier())
        .orElseGet(() ->
            User.createFromSocial(
                socialUserInfo.uniqueIdentifier(),
                socialUserInfo.name(),
                socialUserInfo.profileImageUrl(),
                client.getProviderName()
            )
        );

    user.nullifyDeletedAt();
    user.updateToken(socialToken.socialAccessToken(), socialToken.socialRefreshToken());

    boolean isFirst = user.getNo() == null;

    User savedUser = userRepository.save(user);

    if (isFirst) {
      legendService.create(savedUser.getNo());
    }

    return savedUser;
  }
}
