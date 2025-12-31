package kr.modernworld.modernworldv2.growth.presentation.userachievement.dto.res;

import java.time.Instant;

public record UserAchievementResponseDTO(
    Long no,
    Long userNo,
    Long achievementNo,
    Boolean status,
    Instant createdAt,
    AchievementDetail achievement
) {

}
