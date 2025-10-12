package kr.modernworld.modernworldv2.user.infrastructure.auth.google;

import kr.modernworld.modernworldv2.user.application.oauth.OAuthTokenDTO;
import kr.modernworld.modernworldv2.user.application.oauth.SocialUserInfoDTO;
import kr.modernworld.modernworldv2.user.domain.port.OAuthClient;
import kr.modernworld.modernworldv2.user.domain.user.UserDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GoogleOAuthClient implements OAuthClient {

  private final WebClient webClient;
  private final GoogleOAuthProperties properties;

  private final String BASE_URL = "https://accounts.google.com";

  @Autowired
  public GoogleOAuthClient(WebClient webClient, GoogleOAuthProperties properties) {
    this.webClient = webClient.mutate()
        .baseUrl(BASE_URL)
        .defaultHeader(HttpHeaders.CONTENT_TYPE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .build();

    this.properties = properties;
  }

  @Override
  public UserDomain getProviderName() {
    return UserDomain.google;
  }

  @Override
  public String getLoginUrl(String state) {
    String path = "/o/oauth2/v2/auth";
    String clientId = "?client_id=" + properties.id();
    String redirectUri = "&redirect_uri=" + properties.callbackUrl();
    String responseType = "&response_type=code";
    String scope = "&scope=https://www.googleapis.com/auth/userinfo.profile";
    String accessType = "&access_type=offline";
    String finalState = "&state=" + state;
    String prompt = "&prompt=consent";

    return BASE_URL + path + clientId + redirectUri
        + responseType + scope + accessType + finalState + prompt;
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
