package kr.modernworld.modernworldv2.growth.application.userachievement;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum AchievementTier {
  TIER_1(1, 10L),
  TIER_2(2, 20L),
  TIER_3(3, 40L);

  private final Integer level;
  private final Long count;

  AchievementTier(Integer level, Long count) {
    this.level = level;
    this.count = count;
  }

  public static Optional<AchievementTier> findByCount(Long count) {
    return Arrays.stream(values())
        .filter(tier -> tier.count.equals(count))
        .findFirst();
  }
}
