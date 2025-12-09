package kr.modernworld.modernworldv2.user.infrastructure.auth.google;

import kr.modernworld.modernworldv2.global.exception.OAuthException;
import kr.modernworld.modernworldv2.user.application.auth.OAuthTokenDTO;
import kr.modernworld.modernworldv2.user.application.auth.SocialUserInfoDTO;
import kr.modernworld.modernworldv2.user.domain.auth.port.OAuthClient;
import kr.modernworld.modernworldv2.user.domain.user.UserDomain;
import kr.modernworld.modernworldv2.user.infrastructure.auth.google.dto.GoogleApiErrorDTO;
import kr.modernworld.modernworldv2.user.infrastructure.auth.google.dto.GoogleTokenFailDTO;
import kr.modernworld.modernworldv2.user.infrastructure.auth.google.dto.GoogleTokenSuccessDTO;
import kr.modernworld.modernworldv2.user.infrastructure.auth.google.dto.GoogleUserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service
public class GoogleOAuthClient implements OAuthClient {

  private final WebClient authWebClient;
  private final WebClient apiWebClient;
  private final GoogleOAuthProperties properties;

  @Autowired
  public GoogleOAuthClient(WebClient webClient, GoogleOAuthProperties properties) {
    this.authWebClient = webClient.mutate()
        .baseUrl("https://oauth2.googleapis.com")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .build();

    this.apiWebClient = webClient.mutate()
        .baseUrl("https://www.googleapis.com")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
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

    return UriComponentsBuilder
        .fromUriString("https://accounts.google.com")
        .path(path)
        .queryParam("client_id", properties.id())
        .queryParam("redirect_uri", properties.callbackUrl())
        .queryParam("response_type", "code")
        .queryParam("scope", "https://www.googleapis.com/auth/userinfo.profile")
        .queryParam("access_type", "offline")
        .queryParam("state", state)
        .queryParam("prompt", "consent").encode().toUriString();
  }

  @Override
  public OAuthTokenDTO getSocialToken(String state, String authCode) {
    GoogleTokenSuccessDTO response = getGoogleToken(authCode);

    return new OAuthTokenDTO(response.accessToken(), response.refreshToken(), response.expiresIn(),
        response.refreshTokenExpiresIn().orElse(response.expiresIn()));
  }

  private GoogleTokenSuccessDTO getGoogleToken(String authCode) {
    return authWebClient
        .post()
        .uri("/token")
        .body(BodyInserters.fromFormData("code", authCode)
            .with("client_id", properties.id())
            .with("client_secret", properties.secret())
            .with("redirect_uri", properties.callbackUrl())
            .with("grant_type", "authorization_code")
        )
        .exchangeToMono(res -> {
          if (res.statusCode().isError()) {
            return res.bodyToMono(GoogleTokenFailDTO.class).flatMap(error ->
                Mono.error(new OAuthException(
                    "[GoogleOAuthClient] OAuth token error: " + error.error().orElse(null)
                        + ", Description: " + error.errorDescription().orElse(null))));
          }
          return res.bodyToMono(GoogleTokenSuccessDTO.class);
        })
        .switchIfEmpty(Mono.error(
            new IllegalStateException("[GoogleOAuthClient] Google token response body is empty.")))
        .block();
  }

  @Override
  public SocialUserInfoDTO getSocialUserInfo(String socialAccessToken) {
    String uri = "/oauth2/v2/userinfo";

    GoogleUserInfoDTO response = getGoogleUserInfo(socialAccessToken, uri);

    return new SocialUserInfoDTO(response.id(), response.name(), response.picture());
  }

  private GoogleUserInfoDTO getGoogleUserInfo(String socialAccessToken, String uri) {
    return apiWebClient
        .get()
        .uri(uri)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + socialAccessToken)
        .exchangeToMono(res -> {
          if (res.statusCode().isError()) {
            return res.bodyToMono(GoogleApiErrorDTO.class)
                .flatMap(error -> Mono.error(new OAuthException(
                    "[GoogleOAuthClient] OAuth token error: " + error.error().code().orElse(null)
                        + ", Description: " + error.error().message().orElse(null)
                        + ", status: " + error.error().status().orElse(null))));
          }

          return res.bodyToMono(GoogleUserInfoDTO.class);
        })
        .switchIfEmpty(Mono.error(
            new IllegalStateException("[GoogleOAuthClient] Google API response body is empty.")))
        .block();
  }

}
