package kr.modernworld.modernworldv2.member.domain.user.socialtoken.port;

import java.util.Optional;

public interface SocialTokenQueryRepository {

  Optional<String> findSocialAccessTokenByUserNo(Long userNo);

}
