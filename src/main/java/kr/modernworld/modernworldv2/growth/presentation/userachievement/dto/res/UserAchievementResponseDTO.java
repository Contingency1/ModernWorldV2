package kr.modernworld.modernworldv2.growth.presentation.userachievement.dto.res;

import java.time.Instant;
import kr.modernworld.modernworldv2.growth.application.userachievement.dto.GetUserAchievementDTO;

public record UserAchievementResponseDTO(
    Long no,
    Long userNo,
    Long achievementNo,
    Boolean status,
    Instant createdAt,
    GetAchievementResponseDTO achievement
) {

  public static UserAchievementResponseDTO from(GetUserAchievementDTO achievement) {
    return new UserAchievementResponseDTO(achievement.no(),
        achievement.userNo(),
        achievement.achievementNo(),
        achievement.status(),
        achievement.createdAt(),
        GetAchievementResponseDTO.from(achievement.achievement()));
  }
}
