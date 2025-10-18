package kr.modernworld.modernworldv2.user.domain.port;

import kr.modernworld.modernworldv2.user.application.oauth.OAuthTokenDTO;
import kr.modernworld.modernworldv2.user.application.oauth.SocialUserInfoDTO;
import kr.modernworld.modernworldv2.user.domain.user.UserDomain;

public interface OAuthClient {

  UserDomain getProviderName();

  String getLoginUrl(String state);

  OAuthTokenDTO getSocialToken(String state, String authCode);

  SocialUserInfoDTO getSocialUserInfo(String socialAccessToken);

}
