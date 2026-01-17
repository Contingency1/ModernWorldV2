package kr.modernworld.modernworldv2.member.infrastructure.auth.kakao;

import kr.modernworld.modernworldv2.global.exception.OAuthException;
import kr.modernworld.modernworldv2.member.application.auth.OAuthTokenDTO;
import kr.modernworld.modernworldv2.member.application.auth.SocialUserInfoDTO;
import kr.modernworld.modernworldv2.member.domain.auth.port.OAuthClient;
import kr.modernworld.modernworldv2.member.domain.user.UserDomain;
import kr.modernworld.modernworldv2.member.infrastructure.auth.kakao.dto.KakaoAPIErrorDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.kakao.dto.KakaoTokenFailDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.kakao.dto.KakaoTokenSuccessDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.kakao.dto.KakaoUserDetailsDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.kakao.dto.KakaoUserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service("kakaoOAuthClient")
public class KakaoOAuthClient implements OAuthClient {

  private final WebClient apiWebClient;
  private final WebClient tokenWebClient;
  private final KakaoOAuthProperties properties;

  @Autowired
  public KakaoOAuthClient(WebClient webClient, KakaoOAuthProperties properties) {
    this.tokenWebClient = webClient.mutate()
        .baseUrl("https://kauth.kakao.com")
        .defaultHeader(HttpHeaders.CONTENT_TYPE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8")
        .build();

    this.apiWebClient = webClient.mutate()
        .baseUrl("https://kapi.kakao.com")
        .defaultHeader(HttpHeaders.CONTENT_TYPE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8")
        .build();

    this.properties = properties;
  }

  @Override
  public UserDomain getProviderName() {
    return UserDomain.KAKAO;
  }

  @Override
  public String getLoginUrl(String state) {

    return UriComponentsBuilder
        .fromUriString("https://kauth.kakao.com")
        .path("/oauth/authorize")
        .queryParam("response_type", "code")
        .queryParam("client_id", properties.id())
        .queryParam("redirect_uri", properties.callbackUrl())
        .queryParam("state", state)
        .encode()
        .toUriString();
  }

  @Override
  public OAuthTokenDTO getSocialToken(String state, String authCode) {
    String path = "/oauth/token";

    KakaoTokenSuccessDTO response = getKakaoToken(authCode, path);

    return new OAuthTokenDTO(response.accessToken(), response.refreshToken().orElseThrow(),
        response.expiresIn(),
        response.refreshTokenExpiresIn().orElse(response.expiresIn()));
  }

  private KakaoTokenSuccessDTO getKakaoToken(String authCode, String path) {
    return tokenWebClient.post()
        .uri(path)
        .body(BodyInserters
            .fromFormData("grant_type", "authorization_code")
            .with("client_id", properties.id())
            .with("redirect_uri", properties.callbackUrl())
            .with("code", authCode)
            .with("client_secret", properties.secret())
        ).exchangeToMono(res -> {
          if (res.statusCode().isError()) {
            return res.bodyToMono(KakaoTokenFailDTO.class)
                .flatMap(error -> Mono.error(new OAuthException(
                    "[KakaoOAuthClient] OAuth token error: " + error.error().orElse(null)
                        + ", Description: " + error.errorDescription().orElse(null))));
          }

          return res.bodyToMono(KakaoTokenSuccessDTO.class);
        })
        .switchIfEmpty(
            Mono.error(
                new IllegalStateException(
                    "[KakaoOAuthClient] Kakao Auth API response body is empty.")))
        .block();
  }

  @Override
  public SocialUserInfoDTO getSocialUserInfo(String socialAccessToken) {

    KakaoUserInfoDTO result = getKakaoUserInfo(socialAccessToken);

    KakaoUserDetailsDTO userInfo = result.properties();

    return new SocialUserInfoDTO(
        result.id().toString(),
        userInfo.nickname(),
        userInfo.profileImage()
    );
  }

  @Override
  public void unlink(String socialAccessToken) {
    String uri = "/v1/user/unlink";

    apiWebClient
        .post()
        .uri(uri)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + socialAccessToken)
        .retrieve()
        .onStatus(HttpStatusCode::isError, response -> Mono.error(
            new IllegalStateException("[KakaoOAuthClient] Revoke api failed.")))
        .toBodilessEntity()
        .block();
  }

  @Override
  public String getSocialImage(String socialAccessToken) {
    return getSocialUserInfo(socialAccessToken).profileImageUrl();
  }

  private KakaoUserInfoDTO getKakaoUserInfo(String socialAccessToken) {
    String uri = "/v2/user/me";
    String secure = "?secure_resource=true";

    return apiWebClient.get()
        .uri(uri + secure)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + socialAccessToken)
        .exchangeToMono(res -> {
          if (res.statusCode().isError()) {
            return res.bodyToMono(KakaoAPIErrorDTO.class)
                .flatMap(error -> Mono.error(new OAuthException(
                    "[KakaoOAuthClient] OAuth token error: " + error.code().orElse(null)
                        + ", Description: " + error.msg().orElse(null))));
          }

          return res.bodyToMono(KakaoUserInfoDTO.class);
        })
        .switchIfEmpty(
            Mono.error(
                new IllegalStateException("[KakaoOAuthClient] Kakao API response body is empty.")))
        .block();
  }

}
