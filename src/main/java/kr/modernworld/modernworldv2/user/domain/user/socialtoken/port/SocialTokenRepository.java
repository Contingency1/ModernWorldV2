package kr.modernworld.modernworldv2.user.domain.user.socialtoken.port;

import kr.modernworld.modernworldv2.user.domain.user.UserSocialToken;

public interface SocialTokenRepository {

  UserSocialToken save(UserSocialToken token, Long userNo);

}
