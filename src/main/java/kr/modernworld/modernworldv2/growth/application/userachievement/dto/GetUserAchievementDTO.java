package kr.modernworld.modernworldv2.growth.application.userachievement.dto;

import java.time.Instant;

public record GetUserAchievementDTO(
    Long no,
    Long userNo,
    Long achievementNo,
    Boolean status,
    Instant createdAt,
    GetAchievementDTO achievement
) {

}
