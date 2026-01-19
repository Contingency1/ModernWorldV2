package kr.modernworld.modernworldv2.member.infrastructure.auth.token;

import java.time.Duration;
import java.util.Optional;
import kr.modernworld.modernworldv2.member.domain.token.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepositoryImpl implements RedisRepository {

  private static final String RT_PREFIX = "[RT]";
  private static final String LOCK_PREFIX = "[LOCK]";

  private final RedisTemplate<String, String> redisTemplate;

  @Override
  public void saveRefreshToken(Long userNo, String refreshToken, Long expiredAt) {
    long currentTime = System.currentTimeMillis();
    long timeToLive = expiredAt - currentTime;

    if (timeToLive < 0) {
      throw new IllegalArgumentException("Expired after " + timeToLive + " [ms]");
    }

    redisTemplate.opsForValue()
        .set(RT_PREFIX + userNo, refreshToken, Duration.ofMillis(timeToLive));
  }

  @Override
  public Optional<String> findRefreshTokenByUserNo(Long userNo) {
    String response = redisTemplate.opsForValue().get(RT_PREFIX + userNo);

    if (response == null) {
      return Optional.empty();
    }

    return Optional.of(response);
  }

  @Override
  public void deleteRefreshTokenByUserNo(Long userNo) {
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

  @Override
  public void saveSession(String sessionKey, String state, Long expiredAt) {
    long currentTime = System.currentTimeMillis();
    long timeToLive = expiredAt - currentTime;

    if (timeToLive < 0) {
      throw new IllegalArgumentException("Expired after " + timeToLive + " [ms]");
    }

    redisTemplate.opsForValue().set(sessionKey, state, Duration.ofMillis(timeToLive));
  }

  @Override
  public Optional<String> findSessionValueBySessionKey(String sessionKey) {
    String response = redisTemplate.opsForValue().get(sessionKey);

    if (response == null) {
      return Optional.empty();
    }

    return Optional.of(response);
  }

  @Override
  public void deleteSessionBySessionKey(String sessionKey) {
    redisTemplate.delete(sessionKey);
  }
}
