package kr.modernworld.modernworldv2.member.domain.auth.port;


import java.util.Optional;

public interface RedisRepository {

  void saveRefreshToken(Long userNo, String refreshToken, Long expiredAt);

  Optional<String> findRefreshTokenByUserNo(Long userNo);

  void deleteRefreshTokenByUserNo(Long userNo);

  boolean tryLock(Long userNo);

  void unlock(Long userNo);

//  void saveSession(String sessionKey, String state, Long expiredAt);
//
//  Optional<String> findSessionValueBySessionKey(String sessionKey);
//
//  void deleteSessionBySessionKey(String sessionKey);
}
