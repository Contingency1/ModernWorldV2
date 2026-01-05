package kr.modernworld.modernworldv2.member.domain.auth.port;

public interface SessionRepository {

  void save(String sessionKey, String value);

  String findValue(String sessionKey);

  void invalidate();
}
