package kr.modernworld.modernworldv2.user.domain.token;


public interface RefreshTokenRepository {

  void save(Long userNo, String refreshToken, Long expiredAt);

  String findByUserNo(Long userNo);

  void delete(Long userNo);
}
