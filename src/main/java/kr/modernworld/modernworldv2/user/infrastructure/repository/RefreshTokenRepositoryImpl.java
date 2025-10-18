package kr.modernworld.modernworldv2.user.infrastructure.repository;

import java.time.Duration;
import kr.modernworld.modernworldv2.user.domain.port.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

  private final RedisTemplate<String, String> redisTemplate;

  @Override
  public void save(Long userNo, String refreshToken, Long expiredAt) {
    final long ttlMillis = expiredAt - System.currentTimeMillis();

    if (ttlMillis < 0) {
      throw new IllegalArgumentException("Expired after " + ttlMillis + " seconds");
    }

    redisTemplate.opsForValue()
        .set("[RT]" + userNo, refreshToken, Duration.ofMillis(ttlMillis));
  }

  @Override
  public String findByUserNo(Long userNo) {
    return "";
  }

  @Override
  public void delete(Long userNo) {

  }
}
