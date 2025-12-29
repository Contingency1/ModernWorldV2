package kr.modernworld.modernworldv2.user.domain.token;


import java.util.Optional;

public interface RefreshTokenRepository {

  void save(Long userNo, String refreshToken, Long expiredAt);

  Optional<String> findByUserNo(Long userNo);

  void delete(Long userNo);
}
