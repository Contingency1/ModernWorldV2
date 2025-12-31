package kr.modernworld.modernworldv2.member.infrastructure.auth.token;

import java.time.Duration;
import java.util.Optional;
import kr.modernworld.modernworldv2.member.domain.token.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

  private static final String RT_PREFIX = "[RT]";
  private static final String LOCK_PREFIX = "[LOCK]";

  private final RedisTemplate<String, String> redisTemplate;

  @Override
  public void save(Long userNo, String refreshToken, Long expiredAt) {
    final long ttlMillis = expiredAt - System.currentTimeMillis();

    if (ttlMillis < 0) {
      throw new IllegalArgumentException("Expired after " + ttlMillis + " seconds");
    }

    redisTemplate.opsForValue()
        .set(RT_PREFIX + userNo, refreshToken, Duration.ofMillis(ttlMillis));
  }

  @Override
  public Optional<String> findByUserNo(Long userNo) {
    String response = redisTemplate.opsForValue().get(RT_PREFIX + userNo);

    if (response == null) {
      return Optional.empty();
    }

    return Optional.of(response);
  }

  @Override
  public void delete(Long userNo) {
    redisTemplate.delete(RT_PREFIX + userNo);
  }

  @Override
  public boolean tryLock(Long userNo) {
    return Boolean.TRUE.equals(redisTemplate.opsForValue()
        .setIfAbsent(LOCK_PREFIX + userNo, "FLAG FOR LOCK", Duration.ofSeconds(5)));
  }

  @Override
  public void unlock(Long userNo) {
    redisTemplate.delete(LOCK_PREFIX + userNo);
  }
}
