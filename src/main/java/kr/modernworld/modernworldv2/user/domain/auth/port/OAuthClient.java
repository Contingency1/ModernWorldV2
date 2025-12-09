package kr.modernworld.modernworldv2.user.domain.auth.port;

import kr.modernworld.modernworldv2.user.application.auth.OAuthTokenDTO;
import kr.modernworld.modernworldv2.user.application.auth.SocialUserInfoDTO;
import kr.modernworld.modernworldv2.user.domain.user.UserDomain;

public interface OAuthClient {

  UserDomain getProviderName();

  String getLoginUrl(String state);

  OAuthTokenDTO getSocialToken(String state, String authCode);

  SocialUserInfoDTO getSocialUserInfo(String socialAccessToken);

}
