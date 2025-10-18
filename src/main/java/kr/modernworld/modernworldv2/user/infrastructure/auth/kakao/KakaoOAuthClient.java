package kr.modernworld.modernworldv2.user.infrastructure.auth.kakao;

import kr.modernworld.modernworldv2.user.application.oauth.OAuthTokenDTO;
import kr.modernworld.modernworldv2.user.application.oauth.SocialUserInfoDTO;
import kr.modernworld.modernworldv2.user.domain.port.OAuthClient;
import kr.modernworld.modernworldv2.user.domain.user.UserDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service("kakaoOAuthClient")
public class KakaoOAuthClient implements OAuthClient {

  private final WebClient webClient;
  private final KakaoOAuthProperties properties;

  private final String BASE_URL = "https://kauth.kakao.com";

  @Autowired
  public KakaoOAuthClient(WebClient webClient, KakaoOAuthProperties properties) {
    this.webClient = webClient.mutate()
        .baseUrl(BASE_URL)
        .defaultHeader(HttpHeaders.CONTENT_TYPE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .build();
    this.properties = properties;
  }

  @Override
  public UserDomain getProviderName() {
    return UserDomain.kakao;
  }

  @Override
  public String getLoginUrl(String state) {
    String path = "/oauth/authorize";
    String responseType = "?response_type=code";
    String clientId = "&client_id=" + properties.id();
    String redirectUri = "&redirect_uri=" + properties.callbackUrl();
    String finalState = "&state=" + state;

    return BASE_URL + path + responseType + clientId + redirectUri + finalState;
  }

  @Override
  public OAuthTokenDTO getSocialToken(String state, String authCode) {
    return null;
  }

  @Override
  public SocialUserInfoDTO getSocialUserInfo(String socialAccessToken) {
    return null;
  }

}
