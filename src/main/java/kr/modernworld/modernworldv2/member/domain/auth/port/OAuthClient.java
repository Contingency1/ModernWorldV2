package kr.modernworld.modernworldv2.member.domain.auth.port;

import kr.modernworld.modernworldv2.member.application.auth.OAuthTokenDTO;
import kr.modernworld.modernworldv2.member.application.auth.SocialUserInfoDTO;
import kr.modernworld.modernworldv2.member.domain.user.UserDomain;

public interface OAuthClient {

  UserDomain getProviderName();

  String getLoginUrl(String state);

  OAuthTokenDTO getSocialToken(String state, String authCode);

  SocialUserInfoDTO getSocialUserInfo(String socialAccessToken);

  void unlink(String socialAccessToken);

  String getSocialImage(String socialAccessToken);

}
