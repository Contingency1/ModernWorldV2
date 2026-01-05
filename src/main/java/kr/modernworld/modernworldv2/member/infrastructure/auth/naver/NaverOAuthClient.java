package kr.modernworld.modernworldv2.member.infrastructure.auth.naver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.modernworld.modernworldv2.global.exception.OAuthException;
import kr.modernworld.modernworldv2.member.application.auth.OAuthTokenDTO;
import kr.modernworld.modernworldv2.member.application.auth.SocialUserInfoDTO;
import kr.modernworld.modernworldv2.member.domain.auth.port.OAuthClient;
import kr.modernworld.modernworldv2.member.domain.user.UserDomain;
import kr.modernworld.modernworldv2.member.infrastructure.auth.naver.dto.NaverTokenFailDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.naver.dto.NaverTokenSuccessDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.naver.dto.NaverUserInfoFailDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.naver.dto.NaverUserInfoResponseDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.naver.dto.NaverUserInfoSuccessDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Service("naverOAuthClient")
@Slf4j
public class NaverOAuthClient implements OAuthClient {

  private final WebClient tokenWebClient;
  private final WebClient apiWebClient;
  private final NaverOAuthProperties properties;
  private final ObjectMapper objectMapper;

  @Autowired
  public NaverOAuthClient(WebClient webClient, NaverOAuthProperties properties,
      ObjectMapper objectMapper) {
    this.tokenWebClient = webClient.mutate()
        .baseUrl("https://nid.naver.com")
        .defaultHeader(HttpHeaders.CONTENT_TYPE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .build();

    this.apiWebClient = webClient.
        mutate().
        baseUrl("https://openapi.naver.com")
        .build();

    this.properties = properties;
    this.objectMapper = objectMapper;
  }

  public OAuthTokenDTO getSocialToken(String state, String authorizationCode) {
    NaverTokenSuccessDTO response = getNaverToken(state, authorizationCode);

    return new OAuthTokenDTO(response.accessToken(), response.refreshToken(),
        response.expiresIn(),
        response.expiresIn());
  }

  @Override
  public SocialUserInfoDTO getSocialUserInfo(String socialAccessToken) {
    NaverUserInfoSuccessDTO response = getNaverUserInfo(socialAccessToken);

    NaverUserInfoResponseDTO info = response.response();

    return new SocialUserInfoDTO(info.id(), info.name(), info.profileImage());
  }

  private NaverUserInfoSuccessDTO getNaverUserInfo(String socialAccessToken) {
    return apiWebClient
        .get()
        .uri("/v1/nid/me")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + socialAccessToken)
        .exchangeToMono(res -> {
          if (res.statusCode().isError()) {
            return res.bodyToMono(NaverUserInfoFailDTO.class)
                .flatMap(error -> Mono.error(new OAuthException(
                    "[NaverOAuthClient] error message: " + error.message().orElse(null)
                        + ", error code: " + error.resultCode().orElse(null))));
          }

          return res.bodyToMono(NaverUserInfoSuccessDTO.class);
        })
        .switchIfEmpty(
            Mono.error(new IllegalStateException(
                "[NaverOAuthClient] Naver Auth API response body is empty.")))
        .block();
  }

  private NaverTokenSuccessDTO getNaverToken(String state, String authorizationCode) {
    String tokenUrl = "/oauth2.0/token";

    // 비동기 결과를 동기적으로 기다림
    return tokenWebClient.post()
        .uri(tokenUrl)
        .body(BodyInserters.fromFormData("grant_type", "authorization_code")
            .with("client_id", properties.id())
            .with("client_secret", properties.secret())
            .with("code", authorizationCode)
            .with("state", state))
        .exchangeToMono(res -> {
          if (res.statusCode().isError()) {
            return res.bodyToMono(NaverTokenFailDTO.class)
                .flatMap(error -> Mono.error(new OAuthException(
                    "[NaverOAuthClient] OAuth token error: " + error.error().orElse(null)
                        + ", Description: " + error.errorDescription().orElse(null))));
          }

          return res.bodyToMono(String.class).flatMap(body -> {
            if (body.contains("access_token")) {
              try {
                return Mono.just(objectMapper.readValue(body, NaverTokenSuccessDTO.class));
              } catch (JsonProcessingException e) {
                return Mono.error(new OAuthException("Naver Token Success DTO parsing fail."));
              }
            }

            try {
              NaverTokenFailDTO failDTO = objectMapper.readValue(body, NaverTokenFailDTO.class);
              return Mono.error(new OAuthException(
                  "[NaverOAuthClient] OAuth token error: " + failDTO.error().orElse(null)
                      + ", Description: " + failDTO.errorDescription().orElse(null)));
            } catch (JsonProcessingException e) {
              return Mono.error(new OAuthException("Naver Token Fail DTO parsing fail."));
            }
          });
        })
        .switchIfEmpty(Mono.error(
            new IllegalStateException("[NaverOAuthClient] Naver API response body is empty.")))
        .block();
  }

  @Override
  public UserDomain getProviderName() {
    return UserDomain.naver;
  }

  @Override
  public String getLoginUrl(String state) {
    return UriComponentsBuilder
        .fromUriString("https://nid.naver.com")
        .path("/oauth2.0/authorize")
        .queryParam("response_type", "code")
        .queryParam("client_id", properties.id())
        .queryParam("redirect_uri", properties.callbackUrl())
        .queryParam("state", state)
        .encode()
        .toUriString();
  }
}
