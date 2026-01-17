package kr.modernworld.modernworldv2.member.application.user.socialtoken;

import kr.modernworld.modernworldv2.member.domain.user.UserSocialToken;
import kr.modernworld.modernworldv2.member.domain.user.socialtoken.port.SocialTokenQueryRepository;
import kr.modernworld.modernworldv2.member.domain.user.socialtoken.port.SocialTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SocialTokenService {

  private final SocialTokenRepository socialTokenRepository;
  private final SocialTokenQueryRepository socialTokenQueryRepository;

  @Transactional
  public UserSocialToken save(UserSocialToken userSocialToken, Long userNo) {
    return socialTokenRepository.save(userSocialToken, userNo);
  }

  @Transactional
  public String getSocialAccessToken(Long userNo) {
    return socialTokenQueryRepository.findSocialAccessTokenByUserNo(userNo)
        .orElseThrow(() -> new RuntimeException("USER DOESN'T HAVE SOCIAL TOKEN"));
  }

}
