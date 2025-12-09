package kr.modernworld.modernworldv2.user.domain.auth.port;

public interface SessionRepository {

  void save(String sessionKey, String value);

  String findValue(String sessionKey);

  void invalidate();
}
