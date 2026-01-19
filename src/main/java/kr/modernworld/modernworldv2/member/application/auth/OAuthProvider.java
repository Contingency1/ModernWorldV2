package kr.modernworld.modernworldv2.member.application.auth;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import kr.modernworld.modernworldv2.member.domain.auth.port.OAuthClient;
import kr.modernworld.modernworldv2.member.domain.user.UserDomain;
import org.springframework.stereotype.Component;

@Component
public class OAuthProvider {

  private final Map<UserDomain, OAuthClient> clients;

  public OAuthProvider(List<OAuthClient> clients) {
    this.clients = clients
        .stream()
        .collect(
            Collectors.toUnmodifiableMap(
                OAuthClient::getProviderName
                , Function.identity()) // 안에 들어가 보면 static 으로 선언돼 있음, 이는 곧 자기 자신을 그냥 반환하는 역할임
        );
  }

  public OAuthClient getOAuthClient(UserDomain domain) {
    OAuthClient client = clients.get(domain);

    if (client == null) {
      throw new IllegalArgumentException("No client with id " + domain);
    }

    return client;
  }

}
